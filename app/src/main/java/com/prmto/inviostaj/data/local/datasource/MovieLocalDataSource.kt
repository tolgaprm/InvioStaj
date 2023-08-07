package com.prmto.inviostaj.data.local.datasource

import com.prmto.inviostaj.data.remote.dto.Movie
import kotlinx.coroutines.flow.Flow

interface MovieLocalDataSource {
    fun getAllFavoriteMovies(): Flow<List<Movie>>

    suspend fun insertFavoriteMovie(movie: Movie)

    suspend fun deleteFavoriteMovie(movie: Movie)
}