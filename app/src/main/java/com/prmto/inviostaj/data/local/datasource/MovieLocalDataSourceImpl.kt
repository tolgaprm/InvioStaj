package com.prmto.inviostaj.data.local.datasource

import com.prmto.inviostaj.data.local.dao.MovieDao
import com.prmto.inviostaj.data.mapper.toMovie
import com.prmto.inviostaj.data.mapper.toMovieEntity
import com.prmto.inviostaj.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieLocalDataSourceImpl @Inject constructor(
    private val movieDao: MovieDao
) : MovieLocalDataSource {
    override fun getAllFavoriteMovies(): Flow<List<Movie>> {
        return movieDao.getAllFavoriteMovies().map {
            it.map { movieEntity -> movieEntity.toMovie() }
        }
    }

    override suspend fun insertFavoriteMovie(movie: Movie) {
        movieDao.insertFavoriteMovie(movie.toMovieEntity())
    }

    override suspend fun deleteFavoriteMovie(movie: Movie) {
        movieDao.deleteFavoriteMovie(movie.toMovieEntity())
    }
}