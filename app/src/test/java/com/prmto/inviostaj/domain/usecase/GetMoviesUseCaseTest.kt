package com.prmto.inviostaj.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.prmto.inviostaj.constant.Resource
import com.prmto.inviostaj.data.repository.FakeMovieRepository
import com.prmto.inviostaj.data.repository.FakeResponse
import com.prmto.inviostaj.data.repository.MovieRepository
import com.prmto.inviostaj.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class GetMoviesUseCaseTest {

    @get:Rule
    var mainCoroutineRule = MainDispatcherRule()

    private lateinit var getMoviesUseCase: GetMoviesUseCase
    private lateinit var repository: MovieRepository

    @Before
    fun setup() {
        repository = FakeMovieRepository()
        getMoviesUseCase = GetMoviesUseCase(
            repository,
            ConvertDateFormatUseCase(),
            ConvertMovieGenreListToSeparatedByCommaUseCase(repository)
        )
    }

    @Test
    fun `when query is null, then if getTopRatedMovies return Error, result is ResourceError`() =
        runTest {
            passNewRepository(FakeMovieRepository(isSuccess = false))
            val query = null
            val page = 1
            val result = getMoviesUseCase(query, page)
            assertThat(result).isInstanceOf(Resource.Error::class.java)
        }

    @Test
    fun `when query is null, then if getTopRatedMovies return Success, properly updated the movie item`() =
        runTest {
            val query = null
            val page = 1
            val result = getMoviesUseCase(query, page)
            val data = (result as Resource.Success).data
            assertThat(result).isInstanceOf(Resource.Success::class.java)
            assertThat(data).isEqualTo(expectedTopRatedMovies)
        }

    @Test
    fun `when query is not null, then if the getSearchMovies return Error, result is ResourceError`() =
        runTest {
            passNewRepository(FakeMovieRepository(isSuccess = false))
            val query = FakeResponse.oppenheimerQuery
            val page = 1
            val result = getMoviesUseCase(query, page)
            assertThat(result).isInstanceOf(Resource.Error::class.java)
        }

    @Test
    fun `when query is not null, then if the getSearchMovies return Success, properly updated the movie item`() =
        runTest {
            val query = FakeResponse.oppenheimerQuery
            val page = 1
            val result = getMoviesUseCase(query, page)
            val data = (result as Resource.Success).data
            assertThat(result).isInstanceOf(Resource.Success::class.java)
            assertThat(data).isEqualTo(expectedSearchMovies)
        }

    private val expectedTopRatedMovies = listOf(
        FakeResponse.oppenheimerMovie.copy(
            genresBySeparatedByComma = "Drama, History",
            voteCountByString = "1.5k",
            releaseDate = "19 July, 2023"
        ),
        FakeResponse.godFatherMovie.copy(
            genresBySeparatedByComma = "Crime, Drama",
            voteCountByString = "18k",
            releaseDate = "14 March, 1972"
        )
    )

    private val expectedSearchMovies = listOf(
        FakeResponse.oppenheimerMovie.copy(
            genresBySeparatedByComma = "Drama, History",
            voteCountByString = "1.5k",
            releaseDate = "19 July, 2023"
        )
    )

    private fun passNewRepository(repository: MovieRepository) {
        getMoviesUseCase = GetMoviesUseCase(
            repository,
            ConvertDateFormatUseCase(),
            ConvertMovieGenreListToSeparatedByCommaUseCase(repository)
        )
    }
}