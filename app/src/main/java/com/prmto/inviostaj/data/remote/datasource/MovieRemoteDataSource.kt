package com.prmto.inviostaj.data.remote.datasource

import com.prmto.inviostaj.constant.Resource
import com.prmto.inviostaj.data.remote.dto.GenreList
import com.prmto.inviostaj.data.remote.dto.Movie

interface MovieRemoteDataSource {
    suspend fun getTopRatedMovies(page: Int): Resource<List<Movie>>

    suspend fun getMovieGenreList(): Resource<GenreList>

    suspend fun getMovieDetail(movieId: Int): Resource<Movie>

    suspend fun searchMovie(query: String, page: Int): Resource<List<Movie>>
}