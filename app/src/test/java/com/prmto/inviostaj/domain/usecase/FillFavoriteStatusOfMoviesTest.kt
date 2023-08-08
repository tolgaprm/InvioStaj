package com.prmto.inviostaj.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.prmto.inviostaj.data.repository.FakeResponse
import org.junit.Test

class FillFavoriteStatusOfMoviesTest {

    @Test
    fun `invoke should return a list of movies with favorite status`() {
        val fillFavoriteStatusOfMovies = FillFavoriteStatusOfMovies()
        val result = fillFavoriteStatusOfMovies(
            favoriteMovies = FakeResponse.favoriteMovies,
            movies = FakeResponse.responseListOfMovies
        )
        assertThat(result).isEqualTo(FakeResponse.moviesWithFavoriteStatus)
    }
}