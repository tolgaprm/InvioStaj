package com.prmto.inviostaj.data.remote.datasource

import com.prmto.inviostaj.constant.Resource
import com.prmto.inviostaj.data.remote.api.TmdbApi
import com.prmto.inviostaj.data.remote.dto.GenreList
import com.prmto.inviostaj.data.remote.dto.Movie
import com.prmto.inviostaj.data.util.tryCatchFetchDataReturnResource
import javax.inject.Inject

class MovieRemoteDataSourceImpl @Inject constructor(
    private val api: TmdbApi
) : MovieRemoteDataSource {
    override suspend fun getTopRatedMovies(page: Int): Resource<List<Movie>> {
        return tryCatchFetchDataReturnResource {
            api.getTopRatedMovies(page).results
        }
    }

    override suspend fun getMovieGenreList(): Resource<GenreList> {
        return tryCatchFetchDataReturnResource { api.getMovieGenreList() }
    }

    override suspend fun getMovieDetail(movieId: Int): Resource<Movie> {
        return tryCatchFetchDataReturnResource {
            api.getMovieDetail(movieId)
        }
    }

    override suspend fun searchMovie(query: String, page: Int): Resource<List<Movie>> {
        return tryCatchFetchDataReturnResource {
            api.searchMovie(query, page).results
        }
    }
}