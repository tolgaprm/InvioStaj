package com.prmto.inviostaj.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prmto.inviostaj.data.remote.dto.Movie
import com.prmto.inviostaj.data.repository.MovieRepository
import com.prmto.inviostaj.domain.usecase.GetTopRatedMoviePagingDataUseCase
import com.prmto.inviostaj.domain.usecase.ToggleFavoriteMovieUseCase
import com.prmto.inviostaj.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTopRatedMoviePagingDataUseCase: GetTopRatedMoviePagingDataUseCase,
    private val toggleFavoriteMovieUseCase: ToggleFavoriteMovieUseCase,
    private val repository: MovieRepository
) : ViewModel() {

    private val _homeUiState = MutableStateFlow(HomeUiState())
    val state = _homeUiState.asStateFlow()

    private val favoriteMovies = repository.getFavoriteMovies().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    init {
        fetchMovies()
    }

    fun fetchMovies() {
        viewModelScope.launch {
            getTopRatedMoviePagingDataUseCase(page = state.value.currentPage++).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _homeUiState.update {
                            it.copy(
                                isLoading = true,
                                isError = false
                            )
                        }
                    }

                    is Resource.Success -> {
                        updateIsLastPage(isLastPage = resource.data?.isEmpty() ?: true)

                        addNewMovies(movies = resource.data ?: emptyList())

                        updateIsFavoriteMovie(state.value.movies)
                    }

                    is Resource.Error -> {
                        _homeUiState.update {
                            it.copy(
                                isError = true,
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    private fun updateIsLastPage(isLastPage: Boolean) {
        _homeUiState.update {
            it.copy(
                isLastPage = isLastPage
            )
        }
    }

    private fun addNewMovies(movies: List<Movie>) {
        _homeUiState.update {
            it.copy(
                isLoading = false,
                isError = false,
                movies = state.value.movies + movies
            )
        }
    }

    fun toggleFavoriteMovie(movie: Movie) {
        viewModelScope.launch {
            toggleFavoriteMovieUseCase(movie)
        }
    }

    private fun updateIsFavoriteMovie(movie: List<Movie>) {
        viewModelScope.launch {
            favoriteMovies.collectLatest { favoriteMovies ->
                _homeUiState.update {
                    it.copy(
                        movies = movie.map { movieItem ->
                            movieItem.copy(
                                isFavorite = favoriteMovies.any { favoriteMovie ->
                                    favoriteMovie.id == movieItem.id
                                }
                            )
                        }
                    )
                }
            }
        }
    }
}

