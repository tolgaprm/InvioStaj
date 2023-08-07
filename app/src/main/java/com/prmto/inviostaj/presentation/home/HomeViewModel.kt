package com.prmto.inviostaj.presentation.home

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prmto.inviostaj.data.remote.dto.Movie
import com.prmto.inviostaj.domain.usecase.GetTopRatedMoviePagingDataUseCase
import com.prmto.inviostaj.util.onError
import com.prmto.inviostaj.util.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTopRatedMoviePagingDataUseCase: GetTopRatedMoviePagingDataUseCase
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
                getTopRatedMoviePagingDataUseCase(page = state.value.currentPage++)

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

    @VisibleForTesting
    fun updateIsLastPage(isLastPage: Boolean) {
        _homeUiState.update {
            it.copy(
                isLastPage = isLastPage
            )
        }
    }

    @VisibleForTesting
    fun updateHomeStateWithNewMovies(movies: List<Movie>) {
        _homeUiState.update {
            it.copy(
                isLoading = false,
                isError = false,
                movies = state.value.movies + movies
            )
        }
    }

    fun updateIsFavoriteMovie(
        filledFavoriteStatusOfMovies: List<Movie>,
    ) {
        _homeUiState.update {
            it.copy(
                movies = filledFavoriteStatusOfMovies
            )
        }
    }
}