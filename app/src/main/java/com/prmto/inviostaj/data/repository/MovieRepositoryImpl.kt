package com.prmto.inviostaj.data.repository

import com.prmto.inviostaj.data.local.datasource.MovieLocalDataSource
import com.prmto.inviostaj.data.remote.datasource.MovieRemoteDataSource
import com.prmto.inviostaj.data.remote.dto.GenreList
import com.prmto.inviostaj.data.remote.dto.Movie
import com.prmto.inviostaj.data.util.convertToListResource
import com.prmto.inviostaj.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val movieLocalDataSource: MovieLocalDataSource
) : MovieRepository {
    override suspend fun getTopRatedMovies(page: Int): Resource<List<Movie>> {
        val response = movieRemoteDataSource.getTopRatedMovies(page = page)
        return response.convertToListResource()
    }

    override suspend fun getMovieGenreList(): Resource<GenreList> {
        return movieRemoteDataSource.getMovieGenreList()
    }

    override suspend fun getMovieDetail(movieId: Int): Resource<Movie> {
        return movieRemoteDataSource.getMovieDetail(movieId)
    }

    override suspend fun getSearchMovies(query: String, page: Int): Resource<List<Movie>> {
        val response = movieRemoteDataSource.searchMovie(query = query, page = page)
        return response.convertToListResource()
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