package com.prmto.inviostaj.data.repository

import com.prmto.inviostaj.constant.Resource
import com.prmto.inviostaj.data.local.datasource.MovieLocalDataSource
import com.prmto.inviostaj.data.remote.datasource.MovieRemoteDataSource
import com.prmto.inviostaj.data.remote.dto.GenreList
import com.prmto.inviostaj.data.remote.dto.Movie
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val movieLocalDataSource: MovieLocalDataSource
) : MovieRepository {
    override suspend fun getTopRatedMovies(page: Int): Resource<List<Movie>> {
        return movieRemoteDataSource.getTopRatedMovies(page = page)
    }

    override suspend fun getMovieGenreList(): Resource<GenreList> {
        return movieRemoteDataSource.getMovieGenreList()
    }

    override suspend fun getMovieDetail(movieId: Int): Resource<Movie> {
        return movieRemoteDataSource.getMovieDetail(movieId)
    }

    override suspend fun getSearchMovies(query: String, page: Int): Resource<List<Movie>> {
        return movieRemoteDataSource.searchMovie(query = query, page = page)
    }

    override suspend fun getFavoriteMovies(): Resource<List<Movie>> {
        return movieLocalDataSource.getAllFavoriteMovies()
    }

    override suspend fun insertFavoriteMovie(movie: Movie) {
        return movieLocalDataSource.insertFavoriteMovie(movie)
    }

    override suspend fun deleteFavoriteMovie(movie: Movie) {
        return movieLocalDataSource.deleteFavoriteMovie(movie)
    }
}