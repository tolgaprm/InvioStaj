package com.prmto.inviostaj.ui.detail

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.prmto.inviostaj.data.TestConstants
import com.prmto.inviostaj.data.repository.FakeMovieRepository
import com.prmto.inviostaj.data.repository.MovieRepository
import com.prmto.inviostaj.domain.usecase.ConvertDateFormatUseCase
import com.prmto.inviostaj.domain.usecase.GetMovieDetailUseCase
import com.prmto.inviostaj.rules.MainDispatcherRule
import com.prmto.inviostaj.util.Constants
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DetailViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: DetailViewModel
    private lateinit var movieRepository: MovieRepository
    private lateinit var savedStateHandle: SavedStateHandle

    @Before
    fun setUp() {
        savedStateHandle = mockk(relaxed = true)
        every { savedStateHandle.get<Int>("movieId") } returns TestConstants.movieDetailId
        movieRepository = FakeMovieRepository()
        viewModel = DetailViewModel(
            savedStateHandle = savedStateHandle,
            getMovieDetailUseCase = GetMovieDetailUseCase(
                movieRepository = movieRepository,
                convertDateFormatUseCase = ConvertDateFormatUseCase()
            )
        )
    }

    @Test
    fun `when viewModel init, then movieId is not empty and getMovieDetail is success, return ResourceSuccess`() =
        runTest {
            viewModel.detailUiState.test {
                var state = awaitItem()
                assertThat(state.isLoading).isTrue()
                advanceUntilIdle()
                state = awaitItem()
                assertThat(state.isLoading).isFalse()
                assertThat(state.isError).isFalse()
                assertThat(state.movieDetail).isEqualTo(expectedMovieDetail)
            }
        }

    @Test
    fun `when viewModel init, then movieId is not empty getMovieDetail is error, return ResourceError`() =
        runTest {
            passNewRepositoryToViewModel(repository = FakeMovieRepository(isReturnSuccess = false))
            viewModel.detailUiState.test {
                var state = awaitItem()
                assertThat(state.isLoading).isTrue()
                advanceUntilIdle()
                state = awaitItem()
                assertThat(state.isLoading).isFalse()
                assertThat(state.isError).isTrue()
                assertThat(state.movieDetail).isNull()
            }
        }

    private fun passNewRepositoryToViewModel(
        repository: MovieRepository
    ) {
        viewModel = DetailViewModel(
            savedStateHandle = savedStateHandle, getMovieDetailUseCase = GetMovieDetailUseCase(
                movieRepository = repository, convertDateFormatUseCase = ConvertDateFormatUseCase()
            )
        )
    }

    private val expectedMovieDetail = TestConstants.responseMovieDetail.copy(
        imdbId = "tt15398776",
        releaseDate = "19 July, 2023",
        convertedRuntime = mapOf(Constants.HOUR_KEY to "3", Constants.MINUTES_KEY to "1"),
        ratingBarValue = 4.1515f,
        voteCountByString = "1.5k",
        genresBySeparatedByComma = "Drama, History"
    )
}