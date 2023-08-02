package com.prmto.inviostaj.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.prmto.inviostaj.data.local.datasource.MovieLocalDataSource
import com.prmto.inviostaj.data.remote.datasource.MoviePagingSource
import com.prmto.inviostaj.data.remote.datasource.MovieRemoteDataSource
import com.prmto.inviostaj.data.remote.dto.GenreList
import com.prmto.inviostaj.data.remote.dto.Movie
import com.prmto.inviostaj.util.Constants.PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val movieLocalDataSource: MovieLocalDataSource
) : MovieRepository {
    override fun getTopRatedMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = { MoviePagingSource(movieRemoteDataSource) }
        ).flow
    }

    override suspend fun getMovieGenreList(): GenreList {
        return movieRemoteDataSource.getMovieGenreList()
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