package com.prmto.inviostaj.data.remote.datasource.remote

import com.prmto.inviostaj.constant.Resource
import com.prmto.inviostaj.data.TestConstants
import com.prmto.inviostaj.data.remote.datasource.MovieRemoteDataSource
import com.prmto.inviostaj.data.remote.dto.GenreList
import com.prmto.inviostaj.data.remote.dto.Movie
import com.prmto.inviostaj.data.util.createResourceWithStatus

class FakeMovieRemoteDataSource(
    private val isReturnSuccess: Boolean = true
) : MovieRemoteDataSource {
    override suspend fun getTopRatedMovies(page: Int): Resource<List<Movie>> {
        return createResourceWithStatus(isReturnSuccess, TestConstants.favoriteMovies)
    }

    override suspend fun getMovieGenreList(): Resource<GenreList> {
        return createResourceWithStatus(isReturnSuccess, TestConstants.responseGenreList)
    }

    override suspend fun getMovieDetail(movieId: Int): Resource<Movie> {
        return createResourceWithStatus(isReturnSuccess, TestConstants.responseMovieDetail)
    }

    override suspend fun searchMovie(query: String, page: Int): Resource<List<Movie>> {
        return createResourceWithStatus(isReturnSuccess, TestConstants.favoriteMovies)
    }
}