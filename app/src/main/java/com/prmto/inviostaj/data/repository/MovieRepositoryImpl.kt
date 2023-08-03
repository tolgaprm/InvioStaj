package com.prmto.inviostaj.data.repository

import com.prmto.inviostaj.data.local.datasource.MovieLocalDataSource
import com.prmto.inviostaj.data.remote.datasource.MovieRemoteDataSource
import com.prmto.inviostaj.data.remote.dto.GenreList
import com.prmto.inviostaj.data.remote.dto.Movie
import com.prmto.inviostaj.data.remote.dto.MovieDetail
import com.prmto.inviostaj.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val movieLocalDataSource: MovieLocalDataSource
) : MovieRepository {
    override fun getTopRatedMovies(page: Int): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading())
            val result = movieRemoteDataSource.getTopRatedMovies(page = page)

            if (result.isSuccess) {
                val movies = result.getOrNull()?.results ?: emptyList()
                emit(Resource.Success(movies))
            } else {
                emit(
                    Resource.Error(
                        result.exceptionOrNull()?.message ?: ""
                    )
                )
            }
        }
    }

    override suspend fun getMovieGenreList(): GenreList {
        return movieRemoteDataSource.getMovieGenreList()
    }

    override suspend fun getMovieDetail(movieId: Int): Result<MovieDetail> {
        return movieRemoteDataSource.getMovieDetail(movieId)
    }

    override fun getSearchMovies(query: String, page: Int): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading())
            val result = movieRemoteDataSource.searchMovie(query, 1)

            if (result.isSuccess) {
                val movies = result.getOrNull()?.results ?: emptyList()
                emit(Resource.Success(movies))
            } else {
                emit(
                    Resource.Error(
                        result.exceptionOrNull()?.message ?: ""
                    )
                )
            }
        }
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