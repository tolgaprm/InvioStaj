package com.prmto.inviostaj.data.local.datasource

import com.prmto.inviostaj.data.local.dao.MovieDao
import com.prmto.inviostaj.data.remote.dto.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieLocalDataSourceImpl @Inject constructor(
    private val movieDao: MovieDao
) : MovieLocalDataSource {
    override fun getAllFavoriteMovies(): Flow<List<Movie>> {
        return movieDao.getAllFavoriteMovies()
    }

    override suspend fun insertFavoriteMovie(movie: Movie) {
        movieDao.insertFavoriteMovie(movie)
    }

    override suspend fun deleteFavoriteMovie(movie: Movie) {
        movieDao.deleteFavoriteMovie(movie)
    }
}