package com.prmto.inviostaj.ui.home

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.prmto.inviostaj.data.TestConstants
import com.prmto.inviostaj.data.repository.FakeMovieRepository
import com.prmto.inviostaj.data.repository.MovieRepository
import com.prmto.inviostaj.domain.usecase.ConvertDateFormatUseCase
import com.prmto.inviostaj.domain.usecase.ConvertMovieGenreListToSeparatedByCommaUseCase
import com.prmto.inviostaj.domain.usecase.GetMoviesUseCase
import com.prmto.inviostaj.rules.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {
    @get:Rule
    var mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: HomeViewModel
    private lateinit var movieRepository: MovieRepository

    @Before
    fun setUp() {
        movieRepository = FakeMovieRepository()
        viewModel = HomeViewModel(
            GetMoviesUseCase(
                repository = movieRepository,
                convertDateFormatUseCase = ConvertDateFormatUseCase(),
                convertMovieGenreListToSeparatedByCommaUseCase = ConvertMovieGenreListToSeparatedByCommaUseCase(
                    repository = movieRepository
                )
            )
        )
    }

    @Test
    fun `when viewModel init and getTopRatedMovie return Success, update properly homeUiState`() =
        runTest {
            viewModel.state.test {
                var state = awaitItem()
                assertThat(state.isLoading).isTrue()
                assertThat(state.isError).isFalse()
                assertThat(state.currentPage).isEqualTo(1)
                advanceUntilIdle()
                state = awaitItem()
                assertThat(state.isLoading).isFalse()
                assertThat(state.isError).isFalse()
                assertThat(state.isLastPage).isFalse()
                assertThat(state.movies).isEqualTo(expectedTopRatedMovies)
            }
        }

    @Test
    fun `when viewModel init and getTopRatedMovies page1 and page2, update properly homeUiState`() =
        runTest {
            advanceUntilIdle()
            viewModel.fetchMovies()
            viewModel.state.test {
                var state = awaitItem()
                assertThat(state.isLoading).isTrue()
                assertThat(state.isError).isFalse()
                assertThat(state.currentPage).isEqualTo(2)
                advanceUntilIdle()
                state = awaitItem()
                assertThat(state.isLoading).isFalse()
                assertThat(state.isError).isFalse()
                assertThat(state.isLastPage).isFalse()
                assertThat(state.movies).isEqualTo(expectedTopRatedMovies + expectedTopRatedMoviePage2)
            }
        }

    @Test
    fun `when viewModel init and getTopRatedMovie return Error, update properly homeUiState`() =
        runTest {
            passNewRepositoryToViewModel(FakeMovieRepository(isReturnSuccess = false))
            viewModel.state.test {
                var state = awaitItem()
                assertThat(state.isLoading).isTrue()
                assertThat(state.isError).isFalse()
                assertThat(state.currentPage).isEqualTo(1)
                advanceUntilIdle()
                state = awaitItem()
                assertThat(state.isLoading).isFalse()
                assertThat(state.isError).isTrue()
                assertThat(state.movies).isEmpty()
            }
        }

    private fun passNewRepositoryToViewModel(movieRepository: MovieRepository) {
        viewModel = HomeViewModel(
            GetMoviesUseCase(
                repository = movieRepository,
                convertDateFormatUseCase = ConvertDateFormatUseCase(),
                convertMovieGenreListToSeparatedByCommaUseCase = ConvertMovieGenreListToSeparatedByCommaUseCase(
                    repository = movieRepository
                )
            )
        )
    }

    private val expectedTopRatedMovies = listOf(
        TestConstants.oppenheimerMovie.copy(
            genresBySeparatedByComma = "Drama, History",
            voteCountByString = "1.5k",
            releaseDate = "19 July, 2023"
        ), TestConstants.godFatherMovie.copy(
            genresBySeparatedByComma = "Crime, Drama",
            voteCountByString = "18k",
            releaseDate = "14 March, 1972"
        )
    )

    private val expectedTopRatedMoviePage2 = listOf(
        TestConstants.oppenheimerMovie.copy(
            id = 1,
            genresBySeparatedByComma = "Drama, History",
            voteCountByString = "1.5k",
            releaseDate = "19 July, 2023"
        ), TestConstants.godFatherMovie.copy(
            id = 2,
            genresBySeparatedByComma = "Crime, Drama",
            voteCountByString = "18k",
            releaseDate = "14 March, 1972"
        )
    )
}