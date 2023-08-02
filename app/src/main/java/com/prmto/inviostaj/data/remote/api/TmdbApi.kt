package com.prmto.inviostaj.data.remote.api

import com.prmto.inviostaj.data.remote.dto.ApiResponse
import com.prmto.inviostaj.data.remote.dto.GenreList
import com.prmto.inviostaj.data.remote.dto.Movie
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbApi {
    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int
    ): ApiResponse<Movie>

    @GET("genre/movie/list")
    suspend fun getMovieGenreList(): GenreList

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
    }
}