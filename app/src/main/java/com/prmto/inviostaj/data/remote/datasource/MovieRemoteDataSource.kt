package com.prmto.inviostaj.data.remote.datasource

import com.prmto.inviostaj.data.remote.dto.ApiResponse
import com.prmto.inviostaj.data.remote.dto.GenreList
import com.prmto.inviostaj.data.remote.dto.Movie

interface MovieRemoteDataSource {
    suspend fun getTopRatedMovies(page: Int): ApiResponse<Movie>

    suspend fun getMovieGenreList(): GenreList
}