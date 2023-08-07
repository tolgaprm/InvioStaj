package com.prmto.inviostaj.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prmto.inviostaj.data.remote.dto.Movie
import com.prmto.inviostaj.data.repository.MovieRepository
import com.prmto.inviostaj.domain.usecase.FillFavoriteStatusOfMovies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val fillFavoriteStatusOfMovies: FillFavoriteStatusOfMovies
) : ViewModel() {
    private val favoriteMovies = movieRepository.getFavoriteMovies()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _favoriteUiState = MutableStateFlow(FavoriteUiState())
    val favoriteUiState = combine(
        _favoriteUiState,
        favoriteMovies
    ) { _, favoriteMovies ->
        _favoriteUiState.updateAndGet {
            it.copy(
                favoriteMovies = favoriteMovies,
                isLoading = false
            )
        }
    }.onStart {
        _favoriteUiState.updateAndGet { it.copy(isLoading = true) }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        initialValue = FavoriteUiState()
    )

    fun toggleFavoriteMovie(movie: Movie) {
        viewModelScope.launch {
            if (movie.isFavorite) {
                movieRepository.deleteFavoriteMovie(movie)
            } else {
                movieRepository.insertFavoriteMovie(movie.copy(isFavorite = true))
            }
        }
    }

    fun updateIsFavoriteMovie(
        movies: List<Movie>,
        updatedMovies: (List<Movie>) -> Unit,
    ) {
        viewModelScope.launch {
            favoriteMovies.collectLatest { favoriteMovies ->
                val filledFavoriteStatusMovies = fillFavoriteStatusOfMovies(
                    favoriteMovies = favoriteMovies,
                    movies = movies
                )
                updatedMovies(filledFavoriteStatusMovies)
            }
        }
    }
}