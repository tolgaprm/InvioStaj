package com.prmto.inviostaj.ui.favorite

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.prmto.inviostaj.data.TestConstants
import com.prmto.inviostaj.data.repository.FakeMovieRepository
import com.prmto.inviostaj.data.repository.MovieRepository
import com.prmto.inviostaj.domain.usecase.FillFavoriteStatusOfMovies
import com.prmto.inviostaj.rules.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class FavoriteViewModelTest {
    @get:Rule
    var mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: FavoriteViewModel
    private lateinit var movieRepository: MovieRepository

    @Before
    fun setUp() {
        movieRepository = FakeMovieRepository()
        viewModel = FavoriteViewModel(
            movieRepository = movieRepository,
            fillFavoriteStatusOfMovies = FillFavoriteStatusOfMovies()
        )
    }

    @Test
    fun `fetchFavoriteMovies when getFavoriteMovies return Success, uiState properly updated`() =
        runTest {
            viewModel.toggleFavoriteMovie(TestConstants.oppenheimerMovie)
            viewModel.fetchFavoriteMovies()
            viewModel.favoriteUiState.test {
                var state = awaitItem()
                assertThat(state.isLoading).isTrue()
                advanceUntilIdle()
                state = awaitItem()
                assertThat(state.isLoading).isFalse()
                assertThat(state.isError).isFalse()
                assertThat(state.favoriteMovies).isEqualTo(movieRepository.getFavoriteMovies().data)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `fetchFavoriteMovies when getFavoriteMovies return Error, uiState properly updated`() =
        runTest {
            passNewRepositoryToViewModel(FakeMovieRepository(isReturnSuccess = false))
            viewModel.fetchFavoriteMovies()
            viewModel.favoriteUiState.test {
                var state = awaitItem()
                assertThat(state.isLoading).isTrue()
                advanceUntilIdle()
                state = awaitItem()
                assertThat(state.isLoading).isFalse()
                assertThat(state.isError).isTrue()
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `toggleFavoriteMovie, when movie is not favorite, movie added favorite lists`() = runTest {
        viewModel.toggleFavoriteMovie(TestConstants.oppenheimerMovie)
        val exceptedFavoriteMovie = movieRepository.getFavoriteMovies().data
        viewModel.favoriteUiState.test {
            val state = awaitItem()
            assertThat(state.favoriteMovies).isEqualTo(exceptedFavoriteMovie)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `toggleFavoriteMovie, when movie is favorite, movie remove the favorite list`() = runTest {
        val favoriteMovie = TestConstants.oppenheimerMovie.copy(isFavorite = true)
        viewModel.toggleFavoriteMovie(favoriteMovie)
        val exceptedFavoriteMovie = movieRepository.getFavoriteMovies().data
        viewModel.favoriteUiState.test {
            val state = awaitItem()
            assertThat(state.favoriteMovies).isEqualTo(exceptedFavoriteMovie)
            cancelAndIgnoreRemainingEvents()
        }
    }

    private fun passNewRepositoryToViewModel(repository: MovieRepository) {
        viewModel = FavoriteViewModel(
            repository, FillFavoriteStatusOfMovies()
        )
    }
}