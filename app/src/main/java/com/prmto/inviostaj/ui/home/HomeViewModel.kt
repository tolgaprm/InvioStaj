package com.prmto.inviostaj.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prmto.inviostaj.constant.onError
import com.prmto.inviostaj.constant.onSuccess
import com.prmto.inviostaj.data.remote.dto.Movie
import com.prmto.inviostaj.domain.usecase.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase
) : ViewModel() {
    private val _homeUiState = MutableStateFlow(HomeUiState())
    val state = _homeUiState.asStateFlow()

    init {
        fetchMovies()
    }

    fun fetchMovies() {
        viewModelScope.launch {
            _homeUiState.update {
                it.copy(
                    isLoading = true,
                    isError = false
                )
            }
            val resource =
                getMoviesUseCase(page = ++state.value.currentPage)

            resource
                .onSuccess {
                    updateIsLastPage(isLastPage = it?.isEmpty() ?: true)
                    updateHomeStateWithNewMovies(movies = it ?: emptyList())
                }
                .onError {
                    _homeUiState.update {
                        it.copy(
                            isError = true,
                            isLoading = false
                        )
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

    private fun updateHomeStateWithNewMovies(movies: List<Movie>) {
        _homeUiState.update {
            it.copy(
                isLoading = false,
                isError = false,
                movies = state.value.movies + movies
            )
        }
    }

    fun updateIsFavoriteMovie(
        filledFavoriteStatusOfMovies: List<Movie>
    ) {
        _homeUiState.update {
            it.copy(
                movies = filledFavoriteStatusOfMovies
            )
        }
    }
}