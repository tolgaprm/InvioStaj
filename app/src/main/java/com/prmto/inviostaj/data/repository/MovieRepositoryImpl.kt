package com.prmto.inviostaj.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.prmto.inviostaj.data.remote.datasource.MoviePagingSource
import com.prmto.inviostaj.data.remote.datasource.MovieRemoteDataSource
import com.prmto.inviostaj.domain.model.GenreList
import com.prmto.inviostaj.domain.model.Movie
import com.prmto.inviostaj.domain.repository.MovieRepository
import com.prmto.inviostaj.util.Constants.PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieRemoteDataSource: MovieRemoteDataSource
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
}