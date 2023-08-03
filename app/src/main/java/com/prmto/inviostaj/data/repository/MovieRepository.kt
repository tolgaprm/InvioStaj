package com.prmto.inviostaj.data.repository

import com.prmto.inviostaj.data.remote.dto.GenreList
import com.prmto.inviostaj.data.remote.dto.Movie
import com.prmto.inviostaj.data.remote.dto.MovieDetail
import com.prmto.inviostaj.util.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getTopRatedMovies(page: Int): Flow<Resource<List<Movie>>>

    suspend fun getMovieGenreList(): GenreList

    suspend fun getMovieDetail(movieId: Int): Result<MovieDetail>

    fun getSearchMovies(query: String, page: Int): Flow<Resource<List<Movie>>>

    fun getFavoriteMovies(): Flow<List<Movie>>

    suspend fun insertFavoriteMovie(movie: Movie)

    suspend fun deleteFavoriteMovie(movie: Movie)
}