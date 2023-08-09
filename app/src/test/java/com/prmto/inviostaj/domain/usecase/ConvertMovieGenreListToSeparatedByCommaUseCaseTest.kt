package com.prmto.inviostaj.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.prmto.inviostaj.data.repository.FakeMovieRepository
import com.prmto.inviostaj.data.repository.MovieRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class ConvertMovieGenreListToSeparatedByCommaUseCaseTest {
    private lateinit var convertMovieGenreListToSeparatedByCommaUseCase: ConvertMovieGenreListToSeparatedByCommaUseCase
    private lateinit var repository: MovieRepository

    @Before
    fun setUp() {
        repository = FakeMovieRepository()
        convertMovieGenreListToSeparatedByCommaUseCase =
            ConvertMovieGenreListToSeparatedByCommaUseCase(repository)
    }

    @Test
    fun `invoke() should return empty string when genreIds is empty`() = runTest {
        val genreIds = emptyList<Int>()
        val expectedResult = ""
        val actualResult = convertMovieGenreListToSeparatedByCommaUseCase(genreIds)
        assertThat(expectedResult).isEqualTo(actualResult)
    }

    @Test
    fun `invoke() should return empty string when genres is empty`() = runTest {
        repository = FakeMovieRepository(isReturnEmptyGenre = true)
        passNewRepositoryToUseCase(repository = repository)
        val genreIds = listOf(18)
        val expectedResult = ""
        val actualResult = convertMovieGenreListToSeparatedByCommaUseCase(genreIds)
        assertThat(expectedResult).isEqualTo(actualResult)
    }

    @Test
    fun `invoke() should return empty string when getMovieGenreList return error`() = runTest {
        repository = FakeMovieRepository(isReturnSuccess = false)
        passNewRepositoryToUseCase(repository = repository)
        val genreIds = listOf(18)
        val expectedResult = ""
        val actualResult = convertMovieGenreListToSeparatedByCommaUseCase(genreIds)
        assertThat(expectedResult).isEqualTo(actualResult)
    }

    @Test
    fun `invoke() should return genre name separated with comma when genreIds is not empty and genres is not empty`() =
        runTest {
            val genreIds = listOf(18, 36, 10752)
            val expectedResult = "Drama, History, War"
            val actualResult = convertMovieGenreListToSeparatedByCommaUseCase(genreIds)
            assertThat(expectedResult).isEqualTo(actualResult)
        }

    private fun passNewRepositoryToUseCase(
        repository: MovieRepository
    ) {
        convertMovieGenreListToSeparatedByCommaUseCase =
            ConvertMovieGenreListToSeparatedByCommaUseCase(repository)
    }
}