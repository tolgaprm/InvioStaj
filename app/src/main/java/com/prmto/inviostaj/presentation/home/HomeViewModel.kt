package com.prmto.inviostaj.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.prmto.inviostaj.data.remote.dto.Movie
import com.prmto.inviostaj.data.repository.MovieRepository
import com.prmto.inviostaj.domain.usecase.GetTopRatedMoviePagingDataUseCase
import com.prmto.inviostaj.domain.usecase.ToggleFavoriteMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getTopRatedMoviePagingDataUseCase: GetTopRatedMoviePagingDataUseCase,
    private val toggleFavoriteMovieUseCase: ToggleFavoriteMovieUseCase,
    movieRepository: MovieRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HomeUiState())
    val state = _state.asStateFlow()

    val topRatedMoviesPagingData = combine(
        getTopRatedMoviePagingDataUseCase().cachedIn(viewModelScope),
        movieRepository.getFavoriteMovies(),
    ) { pagingData, movie ->
        pagingData.map { pagingMovie ->
            pagingMovie.copy(isFavorite = movie.any { it.id == pagingMovie.id })
        }
    }

    fun updateErrorState(isError: Boolean) {
        _state.update { it.copy(isError = isError) }
    }

    fun updateLoadingState(isLoading: Boolean) {
        _state.update { it.copy(isLoading = isLoading) }
    }

    fun toggleFavoriteMovie(movie: Movie) {
        viewModelScope.launch {
            toggleFavoriteMovieUseCase(movie)
        }
    }
}