package com.prmto.inviostaj.ui.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prmto.inviostaj.constant.onError
import com.prmto.inviostaj.constant.onSuccess
import com.prmto.inviostaj.data.remote.dto.Movie
import com.prmto.inviostaj.domain.usecase.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase
) : ViewModel() {
    private var _exploreUiState = MutableStateFlow(ExploreUiState())
    val exploreUiState = _exploreUiState.asStateFlow()

    private var searchJob: Job? = null

    fun fetchMovies() {
        searchJob?.cancel()
        val query = _exploreUiState.value.query
        searchJob = viewModelScope.launch {
            delay(500)
            if (query.isNotBlank()) {
                updateDefaultStateWithLoadingTrueAndCurrentQueryState()
                val resource = getMoviesUseCase(
                    query = query, page = exploreUiState.value.currentPage++
                )
                resource.onSuccess {
                    updateIsLastPage(isLastPage = it?.isEmpty() ?: true)
                    addNewMovies(movies = it ?: emptyList())
                }.onError {
                    _exploreUiState.update { it.copy(isLoading = false, isError = true) }
                }
            }
        }
    }

    private fun updateDefaultStateWithLoadingTrueAndCurrentQueryState() {
        _exploreUiState.update {
            ExploreUiState(
                isLoading = true, query = exploreUiState.value.query
            )
        }
    }

    fun setQuery(query: String) {
        _exploreUiState.update { it.copy(query = query) }
        fetchMovies()
    }

    private fun updateIsLastPage(isLastPage: Boolean) {
        _exploreUiState.update {
            it.copy(
                isLastPage = isLastPage
            )
        }
    }

    private fun addNewMovies(movies: List<Movie>) {
        _exploreUiState.update {
            it.copy(
                isLoading = false,
                isError = false,
                movies = exploreUiState.value.movies + movies
            )
        }
    }

    fun updateIsFavoriteMovie(
        filledFavoriteStatusOfMovies: List<Movie>,
    ) {
        _exploreUiState.update {
            it.copy(
                movies = filledFavoriteStatusOfMovies
            )
        }
    }
}