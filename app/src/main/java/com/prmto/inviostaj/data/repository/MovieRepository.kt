package com.prmto.inviostaj.data.repository

import com.prmto.inviostaj.data.remote.dto.GenreList
import com.prmto.inviostaj.data.remote.dto.Movie
import com.prmto.inviostaj.util.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getTopRatedMovies(page: Int): Resource<List<Movie>>

    suspend fun getMovieGenreList(): Resource<GenreList>

    suspend fun getMovieDetail(movieId: Int): Resource<Movie>

    suspend fun getSearchMovies(query: String, page: Int): Resource<List<Movie>>

    fun getFavoriteMovies(): Flow<List<Movie>>

    suspend fun insertFavoriteMovie(movie: Movie)

    suspend fun deleteFavoriteMovie(movie: Movie)
}