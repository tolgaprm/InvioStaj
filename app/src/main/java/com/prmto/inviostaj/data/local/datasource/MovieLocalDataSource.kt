package com.prmto.inviostaj.data.local.datasource

import com.prmto.inviostaj.constant.Resource
import com.prmto.inviostaj.data.remote.dto.Movie

interface MovieLocalDataSource {
    suspend fun getAllFavoriteMovies(): Resource<List<Movie>>

    suspend fun insertFavoriteMovie(movie: Movie)

    suspend fun deleteFavoriteMovie(movie: Movie)
}