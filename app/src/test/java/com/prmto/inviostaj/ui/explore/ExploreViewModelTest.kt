package com.prmto.inviostaj.ui.explore

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
class ExploreViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: ExploreViewModel
    private lateinit var movieRepository: MovieRepository

    @Before
    fun setUp() {
        movieRepository = FakeMovieRepository()
        viewModel = ExploreViewModel(
            getMoviesUseCase = GetMoviesUseCase(
                repository = movieRepository,
                convertDateFormatUseCase = ConvertDateFormatUseCase(),
                convertMovieGenreListToSeparatedByCommaUseCase = ConvertMovieGenreListToSeparatedByCommaUseCase(
                    repository = movieRepository
                )
            )
        )
    }

    @Test
    fun `when query is not empty, then getMovies is success, update properly exploreUiState`() =
        runTest {
            val query = TestConstants.oppenheimerQuery
            viewModel.setQuery(query)
            viewModel.fetchMovies()
            viewModel.exploreUiState.test {
                var state = awaitItem()
                assertThat(state.isLoading).isTrue()
                advanceUntilIdle()
                state = awaitItem()
                assertThat(state.query).isEqualTo(query)
                assertThat(state.isLoading).isFalse()
                assertThat(state.isError).isFalse()
                assertThat(state.movies).isEqualTo(expectedSearchMovies)
            }
        }

    private val expectedSearchMovies = listOf(
        TestConstants.oppenheimerMovie.copy(
            genresBySeparatedByComma = "Drama, History",
            voteCountByString = "1.5k",
            releaseDate = "19 July, 2023"
        )
    )
}