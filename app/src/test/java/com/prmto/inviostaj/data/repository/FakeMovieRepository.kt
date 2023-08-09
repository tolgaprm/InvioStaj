package com.prmto.inviostaj.data.repository

import com.prmto.inviostaj.constant.Resource
import com.prmto.inviostaj.data.TestConstants
import com.prmto.inviostaj.data.remote.dto.GenreList
import com.prmto.inviostaj.data.remote.dto.Movie
import com.prmto.inviostaj.data.util.createResourceWithStatus
import kotlinx.coroutines.delay

class FakeMovieRepository(
    private val isReturnSuccess: Boolean = true,
    private val isReturnEmptyGenre: Boolean = false
) : MovieRepository {
    private val favoriteMovies: MutableList<Movie> = mutableListOf()

    override suspend fun getTopRatedMovies(page: Int): Resource<List<Movie>> {
        delay(1000)
        return when (page) {
            1 -> createResourceWithStatus(isReturnSuccess, TestConstants.responseListOfMovies)
            2 -> createResourceWithStatus(isReturnSuccess, TestConstants.responseListOfMovies2)
            else -> createResourceWithStatus(isReturnSuccess, emptyList())
        }
    }

    override suspend fun getMovieGenreList(): Resource<GenreList> {
        val response = if (isReturnEmptyGenre) {
            GenreList(emptyList())
        } else {
            TestConstants.responseGenreList
        }
        return createResourceWithStatus(isReturnSuccess, response)
    }

    override suspend fun getMovieDetail(movieId: Int): Resource<Movie> {
        delay(1000)
        return createResourceWithStatus(
            isReturnSuccess, TestConstants.responseMovieDetail
        )
    }

    override suspend fun getSearchMovies(query: String, page: Int): Resource<List<Movie>> {
        delay(1000)
        return createResourceWithStatus(
            isReturnSuccess,
            TestConstants.responseListOfMovies.filter {
                it.originalTitle.lowercase().contains(query.lowercase())
            }
        )
    }

    override suspend fun getFavoriteMovies(): Resource<List<Movie>> {
        delay(1000)
        return createResourceWithStatus(isReturnSuccess, favoriteMovies.toList())
    }

    override suspend fun insertFavoriteMovie(movie: Movie) {
        favoriteMovies.add(movie)
    }

    override suspend fun deleteFavoriteMovie(movie: Movie) {
        favoriteMovies.remove(movie)
    }
}