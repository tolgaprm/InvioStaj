package com.prmto.inviostaj.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.prmto.inviostaj.constant.Resource
import com.prmto.inviostaj.data.repository.FakeMovieRepository
import com.prmto.inviostaj.data.repository.FakeResponse
import com.prmto.inviostaj.data.repository.MovieRepository
import com.prmto.inviostaj.util.Constants
import com.prmto.inviostaj.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class GetMovieDetailUseCaseTest {

    @get:Rule
    var mainCoroutineRule = MainDispatcherRule()

    private lateinit var getMovieDetailUseCase: GetMovieDetailUseCase
    private lateinit var repository: MovieRepository

    @Before
    fun setUp() {
        repository = FakeMovieRepository()
        getMovieDetailUseCase = GetMovieDetailUseCase(
            movieRepository = repository,
            convertDateFormatUseCase = ConvertDateFormatUseCase()
        )
    }

    @Test
    fun `when invoke GetMovieDetailUseCase then return Error Success`() = runTest {
        repository = FakeMovieRepository(isSuccess = false)
        passNewRepositoryToUseCase(repository = repository)
        val response = getMovieDetailUseCase(movieId = FakeResponse.movieDetailId)
        assertThat(response).isInstanceOf(Resource.Error::class.java)
    }

    @Test
    fun `when invoke GetMovieDetailUseCase then return Success`() = runTest {
        val response = getMovieDetailUseCase(movieId = FakeResponse.movieDetailId)
        assertThat(response).isInstanceOf(Resource.Success::class.java)
    }

    @Test
    fun `when invoke GetMovieDetailUseCase return Success, then properly updated the movie detail item`() =
        runTest {
            val response = getMovieDetailUseCase(movieId = FakeResponse.movieDetailId)
            val movieDetail = (response as Resource.Success).data
            assertThat(movieDetail).isEqualTo(expectedMovieDetail)
        }

    private fun passNewRepositoryToUseCase(
        repository: MovieRepository
    ) {
        getMovieDetailUseCase = GetMovieDetailUseCase(
            movieRepository = repository,
            convertDateFormatUseCase = ConvertDateFormatUseCase()
        )
    }

    private val expectedMovieDetail = FakeResponse.responseMovieDetail.copy(
        imdbId = "tt15398776",
        releaseDate = "19 July, 2023",
        convertedRuntime = mapOf(Constants.HOUR_KEY to "3", Constants.MINUTES_KEY to "1"),
        ratingBarValue = 4.1515f,
        voteCountByString = "1.5k",
        genresBySeparatedByComma = "Drama, History"
    )
}