package com.prmto.inviostaj.data.remote.datasource

import com.prmto.inviostaj.data.remote.api.TmdbApi
import com.prmto.inviostaj.data.remote.dto.GenreList
import com.prmto.inviostaj.data.remote.dto.ListResponse
import com.prmto.inviostaj.data.remote.dto.Movie
import com.prmto.inviostaj.data.util.tryCatchApiCallReturnResource
import com.prmto.inviostaj.util.Resource
import javax.inject.Inject

class MovieRemoteDataSourceImpl @Inject constructor(
    private val api: TmdbApi
) : MovieRemoteDataSource {
    override suspend fun getTopRatedMovies(page: Int): Resource<ListResponse<Movie>> {
        return tryCatchApiCallReturnResource {
            api.getTopRatedMovies(page)
        }
    }

    override suspend fun getMovieGenreList(): Resource<GenreList> {
        return tryCatchApiCallReturnResource { api.getMovieGenreList() }
    }

    override suspend fun getMovieDetail(movieId: Int): Resource<Movie> {
        return tryCatchApiCallReturnResource {
            api.getMovieDetail(movieId)
        }
    }

    override suspend fun searchMovie(query: String, page: Int): Resource<ListResponse<Movie>> {
        return tryCatchApiCallReturnResource {
            api.searchMovie(query, page)
        }
    }
}