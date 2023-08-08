package com.prmto.inviostaj.data.repository

import com.prmto.inviostaj.constant.Resource
import com.prmto.inviostaj.data.remote.dto.GenreList
import com.prmto.inviostaj.data.remote.dto.Movie
import com.prmto.inviostaj.data.util.createResourceWithStatus
import kotlinx.coroutines.delay

class FakeMovieRepository(
    private val isSuccess: Boolean = true,
    private val isReturnEmptyGenre: Boolean = false
) : MovieRepository {

    private val favoriteMovies: MutableList<Movie> = mutableListOf()
    override suspend fun getTopRatedMovies(page: Int): Resource<List<Movie>> {
        return createResourceWithStatus(isSuccess, FakeResponse.responseListOfMovies)
    }

    override suspend fun getMovieGenreList(): Resource<GenreList> {
        val response = if (isReturnEmptyGenre) {
            GenreList(emptyList())
        } else {
            FakeResponse.responseGenreList
        }
        return createResourceWithStatus(isSuccess, response)
    }

    override suspend fun getMovieDetail(movieId: Int): Resource<Movie> {
        return createResourceWithStatus(
            isSuccess, FakeResponse.responseMovieDetail
        )
    }

    override suspend fun getSearchMovies(query: String, page: Int): Resource<List<Movie>> {
        return createResourceWithStatus(
            isSuccess,
            FakeResponse.responseListOfMovies.filter {
                it.originalTitle.lowercase().contains(query.lowercase())
            }
        )
    }

    override suspend fun getFavoriteMovies(): Resource<List<Movie>> {
        delay(1000)
        return createResourceWithStatus(isSuccess, favoriteMovies.toList())
    }

    override suspend fun insertFavoriteMovie(movie: Movie) {
        favoriteMovies.add(movie)
    }

    override suspend fun deleteFavoriteMovie(movie: Movie) {
        favoriteMovies.remove(movie)
    }
}