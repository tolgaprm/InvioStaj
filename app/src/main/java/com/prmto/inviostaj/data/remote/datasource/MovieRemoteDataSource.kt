package com.prmto.inviostaj.data.remote.datasource

import com.prmto.inviostaj.data.remote.dto.GenreList
import com.prmto.inviostaj.data.remote.dto.ListResponse
import com.prmto.inviostaj.data.remote.dto.Movie
import com.prmto.inviostaj.util.Resource

interface MovieRemoteDataSource {
    suspend fun getTopRatedMovies(page: Int): Resource<ListResponse<Movie>>

    suspend fun getMovieGenreList(): Resource<GenreList>

    suspend fun getMovieDetail(movieId: Int): Resource<Movie>

    suspend fun searchMovie(query: String, page: Int): Resource<ListResponse<Movie>>
}