package com.prmto.inviostaj.data.remote.datasource

import com.prmto.inviostaj.data.remote.dto.ApiResponse
import com.prmto.inviostaj.data.remote.dto.MovieDto
import com.prmto.inviostaj.domain.model.GenreList

interface MovieRemoteDataSource {
    suspend fun getTopRatedMovies(page: Int): ApiResponse<MovieDto>

    suspend fun getMovieGenreList(): GenreList
}