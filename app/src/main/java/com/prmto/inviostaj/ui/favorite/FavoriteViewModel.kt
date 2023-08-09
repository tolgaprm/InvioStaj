package com.prmto.inviostaj.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prmto.inviostaj.constant.onError
import com.prmto.inviostaj.constant.onSuccess
import com.prmto.inviostaj.data.remote.dto.Movie
import com.prmto.inviostaj.data.repository.MovieRepository
import com.prmto.inviostaj.domain.usecase.FillFavoriteStatusOfMovies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val fillFavoriteStatusOfMovies: FillFavoriteStatusOfMovies
) : ViewModel() {
    private val _favoriteUiState = MutableStateFlow(FavoriteUiState())
    val favoriteUiState = _favoriteUiState.asStateFlow()

    private var fetchMoviesJob: Job? = null

    init {
        fetchMoviesJob = fetchFavoriteMovies()
    }

    fun fetchFavoriteMovies(): Job {
        return viewModelScope.launch {
            _favoriteUiState.update { it.copy(isLoading = true) }
            val resource = movieRepository.getFavoriteMovies()
            resource.onSuccess { favoriteMovies ->
                _favoriteUiState.update {
                    it.copy(
                        isLoading = false,
                        favoriteMovies = favoriteMovies ?: emptyList()
                    )
                }
            }.onError {
                _favoriteUiState.update {
                    it.copy(
                        isLoading = false,
                        isError = true
                    )
                }
            }
        }
    }

    fun toggleFavoriteMovie(movie: Movie) {
        viewModelScope.launch {
            if (movie.isFavorite) {
                movieRepository.deleteFavoriteMovie(movie)
            } else {
                movieRepository.insertFavoriteMovie(movie.copy(isFavorite = true))
            }
            fetchMoviesJob = fetchFavoriteMovies()
        }
    }

    fun updateIsFavoriteMovie(
        movies: List<Movie>,
        updatedMovies: (List<Movie>) -> Unit
    ) {
        viewModelScope.launch {
            delay(200)
            val filledFavoriteStatusMovies = fillFavoriteStatusOfMovies(
                favoriteMovies = favoriteUiState.value.favoriteMovies,
                movies = movies
            )
            updatedMovies(filledFavoriteStatusMovies)
        }
    }
}