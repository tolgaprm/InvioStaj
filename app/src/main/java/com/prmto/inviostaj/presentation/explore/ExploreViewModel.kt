package com.prmto.inviostaj.presentation.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prmto.inviostaj.data.remote.dto.Movie
import com.prmto.inviostaj.data.repository.MovieRepository
import com.prmto.inviostaj.domain.usecase.GetSearchMovieUseCase
import com.prmto.inviostaj.domain.usecase.ToggleFavoriteMovieUseCase
import com.prmto.inviostaj.domain.usecase.UpdateMovieToIsFavoriteUseCase
import com.prmto.inviostaj.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val getSearchMovieUseCase: GetSearchMovieUseCase,
    private val toggleFavoriteMovieUseCase: ToggleFavoriteMovieUseCase,
    private val updateMovieToIsFavoriteUseCase: UpdateMovieToIsFavoriteUseCase,
    repository: MovieRepository
) : ViewModel() {

    val query = MutableStateFlow("")

    private var _exploreUiState = MutableStateFlow(ExploreUiState())
    val exploreUiState = _exploreUiState.asStateFlow()

    private val favoriteMovies = repository.getFavoriteMovies().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    private var searchJob: Job? = null

    init {
        collectQueryAndFetchMovies()
    }

    fun fetchMovies() {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            if (query.value.isNotBlank()) {
                getSearchMovieUseCase(
                    query = query.value,
                    page = exploreUiState.value.currentPage++
                ).collectLatest { resource ->
                    when (resource) {
                        is Resource.Loading -> {
                            _exploreUiState.update { it.copy(isLoading = true, isError = false) }
                        }

                        is Resource.Success -> {
                            updateIsLastPage(isLastPage = resource.data?.isEmpty() ?: true)

                            addNewMovies(movies = resource.data ?: emptyList())

                            updateIsFavoriteMovie()
                        }

                        is Resource.Error -> {
                            _exploreUiState.update { it.copy(isLoading = false, isError = true) }
                        }
                    }
                }
            }
        }
    }

    private fun collectQueryAndFetchMovies() {
        viewModelScope.launch {
            query.collectLatest { query ->
                delay(1.seconds)
                _exploreUiState.update { ExploreUiState() }
                if (query.isNotBlank()) {
                    fetchMovies()
                }
            }
        }
    }

    private fun updateIsLastPage(isLastPage: Boolean) {
        _exploreUiState.update {
            it.copy(
                isLastPage = isLastPage
            )
        }
    }

    fun toggleFavoriteMovie(movie: Movie) {
        viewModelScope.launch {
            toggleFavoriteMovieUseCase(movie)
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

    private fun updateIsFavoriteMovie() {
        viewModelScope.launch {
            favoriteMovies.collectLatest { favoriteMovies ->
                _exploreUiState.update {
                    it.copy(
                        movies = updateMovieToIsFavoriteUseCase(
                            favoriteMovies = favoriteMovies,
                            movies = exploreUiState.value.movies
                        )
                    )
                }
            }
        }
    }
}