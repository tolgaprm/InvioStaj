package com.prmto.inviostaj.data.remote.api

import com.prmto.inviostaj.data.remote.dto.GenreList
import com.prmto.inviostaj.data.remote.dto.ListResponse
import com.prmto.inviostaj.data.remote.dto.Movie
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi {
    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int
    ): ListResponse<Movie>

    @GET("genre/movie/list")
    suspend fun getMovieGenreList(): GenreList

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int
    ): Movie

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("query") query: String,
        @Query("page") page: Int
    ): ListResponse<Movie>
}