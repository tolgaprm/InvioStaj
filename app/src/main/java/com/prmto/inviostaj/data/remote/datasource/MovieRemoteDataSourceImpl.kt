package com.prmto.inviostaj.data.remote.datasource

import com.prmto.inviostaj.data.remote.api.TmdbApi
import com.prmto.inviostaj.data.remote.dto.ApiResponse
import com.prmto.inviostaj.data.remote.dto.GenreList
import com.prmto.inviostaj.data.remote.dto.Movie
import javax.inject.Inject

class MovieRemoteDataSourceImpl @Inject constructor(
    private val api: TmdbApi
) : MovieRemoteDataSource {
    override suspend fun getTopRatedMovies(page: Int): ApiResponse<Movie> {
        return api.getTopRatedMovies(page)
    }

    override suspend fun getMovieGenreList(): GenreList {
        return api.getMovieGenreList()
    }
}