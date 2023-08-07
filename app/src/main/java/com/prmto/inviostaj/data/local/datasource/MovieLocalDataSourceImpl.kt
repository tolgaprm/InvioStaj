package com.prmto.inviostaj.data.local.datasource

import com.prmto.inviostaj.constant.Resource
import com.prmto.inviostaj.data.local.dao.MovieDao
import com.prmto.inviostaj.data.remote.dto.Movie
import com.prmto.inviostaj.data.util.tryCatchFetchDataReturnResource
import javax.inject.Inject

class MovieLocalDataSourceImpl @Inject constructor(
    private val movieDao: MovieDao
) : MovieLocalDataSource {
    override suspend fun getAllFavoriteMovies(): Resource<List<Movie>> {
        return tryCatchFetchDataReturnResource { movieDao.getAllFavoriteMovies() }
    }

    override suspend fun insertFavoriteMovie(movie: Movie) {
        movieDao.insertFavoriteMovie(movie)
    }

    override suspend fun deleteFavoriteMovie(movie: Movie) {
        movieDao.deleteFavoriteMovie(movie)
    }
}