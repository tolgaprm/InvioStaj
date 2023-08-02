package com.prmto.inviostaj.presentation.home

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.prmto.inviostaj.data.FakeMovieRepository
import com.prmto.inviostaj.data.remote.dto.Movie
import com.prmto.inviostaj.data.repository.MovieRepository
import com.prmto.inviostaj.domain.usecase.ConvertDateFormatUseCase
import com.prmto.inviostaj.domain.usecase.ConvertGenreListToSeparatedByCommaUseCase
import com.prmto.inviostaj.domain.usecase.ConvertVoteCountToKFormatUseCase
import com.prmto.inviostaj.domain.usecase.GetTopRatedMoviePagingDataUseCase
import com.prmto.inviostaj.domain.usecase.ToggleFavoriteMovieUseCase
import com.prmto.inviostaj.util.MainDispatcherRule
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    var dispatcherRule = MainDispatcherRule()


    private lateinit var viewModel: HomeViewModel
    private lateinit var getTopRatedMoviePagingDataUseCase: GetTopRatedMoviePagingDataUseCase
    private lateinit var toggleFavoriteMovieUseCase: ToggleFavoriteMovieUseCase
    private lateinit var repository: MovieRepository


    @Before
    fun setUp() {

        getTopRatedMoviePagingDataUseCase = mockk(relaxed = true)
        toggleFavoriteMovieUseCase = mockk(relaxed = true)

        repository = FakeMovieRepository()

        viewModel = HomeViewModel(
            getTopRatedMoviePagingDataUseCase = GetTopRatedMoviePagingDataUseCase(
                repository = repository,
                convertDateFormatUseCase = ConvertDateFormatUseCase(),
                convertVoteCountToKFormatUseCase = ConvertVoteCountToKFormatUseCase(),
                convertGenreListToSeparatedByCommaUseCase = ConvertGenreListToSeparatedByCommaUseCase()
            ),
            toggleFavoriteMovieUseCase = ToggleFavoriteMovieUseCase(
                repository
            ),
            repository = repository
        )
    }

    @Test
    fun `updateIsLastPage should update isLastPage in state correctly`() = runTest {
        val isLastPage = true

        viewModel.updateIsLastPage(isLastPage)

        viewModel.state.test {
            val currentState = awaitItem()
            assertThat(currentState.isLastPage).isTrue()
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `addNewMovies should add new movies to state correctly`() = runTest {
        val initialState = viewModel.state.value
        val moviesToAdd = listOf(mockk<Movie>(), mockk<Movie>(), mockk<Movie>())

        viewModel.addNewMovies(moviesToAdd)

        viewModel.state.test {
            val currentState = awaitItem()
            assertThat(currentState.isLoading).isFalse()
            assertThat(currentState.isError).isFalse()
            assertThat(initialState.movies.size + moviesToAdd.size).isEqualTo(currentState.movies.size)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `updateIsFavoriteMovie should update isFavorite in movies correctly`() = runTest {

        viewModel.addNewMovies(
            listOf(
                Movie(
                    id = 1,
                    overview = "",
                    posterPath = "",
                    backdropPath = "",
                    voteAverage = 1.0,
                    voteCount = 1,
                    releaseDate = "",
                    genreIds = emptyList(),
                    originalTitle = "",
                    genresBySeparatedByComma = "",
                    isFavorite = true
                ),
                Movie(
                    id = 2,
                    overview = "",
                    posterPath = "",
                    backdropPath = "",
                    voteAverage = 1.0,
                    voteCount = 1,
                    releaseDate = "",
                    genreIds = emptyList(),
                    originalTitle = "",
                    genresBySeparatedByComma = "",
                    isFavorite = true
                )
            )
        )

        viewModel.updateIsFavoriteMovie()

        viewModel.state.test {
            val currentState = awaitItem()
            assertThat(currentState.movies[0].isFavorite).isTrue()
            assertThat(currentState.movies[1].isFavorite).isFalse()
        }
    }
}