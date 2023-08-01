package com.prmto.inviostaj.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prmto.inviostaj.domain.model.Movie
import com.prmto.inviostaj.domain.repository.MovieRepository
import com.prmto.inviostaj.domain.usecase.ToggleFavoriteMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val toggleFavoriteMovieUseCase: ToggleFavoriteMovieUseCase
) : ViewModel() {

    private val _favoriteUiState = MutableStateFlow(FavoriteUiState())
    val favoriteUiState = _favoriteUiState.asStateFlow()

    init {
        viewModelScope.launch {
            movieRepository.getFavoriteMovies()
                .onStart { _favoriteUiState.update { it.copy(isLoading = true) } }
                .collectLatest { favoriteMovies ->
                    _favoriteUiState.update {
                        it.copy(
                            favoriteMovies = favoriteMovies,
                            isLoading = false
                        )
                    }
                }
        }
    }

    fun toggleFavoriteMovie(movie: Movie) {
        viewModelScope.launch {
            toggleFavoriteMovieUseCase(movie)
        }
    }
}