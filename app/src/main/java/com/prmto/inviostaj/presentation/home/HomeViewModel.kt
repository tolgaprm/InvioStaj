package com.prmto.inviostaj.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prmto.inviostaj.data.remote.dto.Movie
import com.prmto.inviostaj.domain.usecase.GetTopRatedMoviePagingDataUseCase
import com.prmto.inviostaj.domain.usecase.ToggleFavoriteMovieUseCase
import com.prmto.inviostaj.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTopRatedMoviePagingDataUseCase: GetTopRatedMoviePagingDataUseCase,
    private val toggleFavoriteMovieUseCase: ToggleFavoriteMovieUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(HomeUiState())
    val state = _state.asStateFlow()

    init {
        fetchMovies()
    }

    fun fetchMovies() {
        viewModelScope.launch {
            getTopRatedMoviePagingDataUseCase(page = state.value.currentPage++).collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        _state.update {
                            it.copy(
                                isLoading = true,
                                isError = false
                            )
                        }
                    }

                    is Resource.Success -> {
                        resource.data?.let {
                            _state.update {
                                it.copy(
                                    isLastPage = resource.data.isEmpty()
                                )
                            }
                        }

                        _state.update { it ->
                            it.copy(
                                isLoading = false,
                                isError = false,
                                movies = resource.data?.let { listOfMovies ->
                                    state.value.movies + listOfMovies
                                } ?: emptyList()
                            )
                        }
                    }

                    is Resource.Error -> {
                        _state.update {
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

    fun toggleFavoriteMovie(movie: Movie) {
        viewModelScope.launch {
            toggleFavoriteMovieUseCase(movie)
            updateIsFavoriteMovie(movie)
        }
    }

    private fun updateIsFavoriteMovie(movie: Movie) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    movies = state.value.movies.map { movieItem ->
                        if (movieItem.id == movie.id) {
                            movieItem.copy(isFavorite = !movieItem.isFavorite)
                        } else {
                            movieItem
                        }
                    }
                )
            }
        }
    }
}

