package com.prmto.inviostaj.data.repository

import com.prmto.inviostaj.data.local.datasource.MovieLocalDataSource
import com.prmto.inviostaj.data.remote.datasource.MovieRemoteDataSource
import com.prmto.inviostaj.data.remote.dto.GenreList
import com.prmto.inviostaj.data.remote.dto.Movie
import com.prmto.inviostaj.data.remote.dto.MovieDetail
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val movieLocalDataSource: MovieLocalDataSource
) : MovieRepository {
    override suspend fun getTopRatedMovies(page: Int): List<Movie> {
        return movieRemoteDataSource.getTopRatedMovies(page = page).results
    }

    override suspend fun getMovieGenreList(): GenreList {
        return movieRemoteDataSource.getMovieGenreList()
    }

    override suspend fun getMovieDetail(movieId: Int): Result<MovieDetail> {
        return movieRemoteDataSource.getMovieDetail(movieId)
    }

    override fun getFavoriteMovies(): Flow<List<Movie>> {
        return movieLocalDataSource.getAllFavoriteMovies()
    }

    override suspend fun insertFavoriteMovie(movie: Movie) {
        return movieLocalDataSource.insertFavoriteMovie(movie)
    }

    override suspend fun deleteFavoriteMovie(movie: Movie) {
        return movieLocalDataSource.deleteFavoriteMovie(movie)
    }
}