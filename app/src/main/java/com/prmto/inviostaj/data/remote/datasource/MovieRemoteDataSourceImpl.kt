package com.prmto.inviostaj.data.remote.datasource

import com.prmto.inviostaj.data.remote.api.TmdbApi
import com.prmto.inviostaj.data.remote.dto.ApiResponse
import com.prmto.inviostaj.data.remote.dto.GenreList
import com.prmto.inviostaj.data.remote.dto.Movie
import com.prmto.inviostaj.data.remote.dto.MovieDetail
import javax.inject.Inject

class MovieRemoteDataSourceImpl @Inject constructor(
    private val api: TmdbApi
) : MovieRemoteDataSource {
    override suspend fun getTopRatedMovies(page: Int): Result<ApiResponse<Movie>> {
        return try {
            val response = api.getTopRatedMovies(page = page)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getMovieGenreList(): GenreList {
        return api.getMovieGenreList()
    }

    override suspend fun getMovieDetail(movieId: Int): Result<MovieDetail> {
        return try {
            val response = api.getMovieDetail(movieId = movieId)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun searchMovie(query: String, page: Int): Result<ApiResponse<Movie>> {
        return try {
            val response = api.searchMovie(query, page)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}