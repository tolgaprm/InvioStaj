package com.prmto.inviostaj.data.repository

import com.prmto.inviostaj.data.remote.dto.GenreList
import com.prmto.inviostaj.data.remote.dto.Movie
import com.prmto.inviostaj.data.remote.dto.MovieDetail
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun getTopRatedMovies(page: Int): List<Movie>

    suspend fun getMovieGenreList(): GenreList

    suspend fun getMovieDetail(movieId: Int): Result<MovieDetail>

    fun getFavoriteMovies(): Flow<List<Movie>>

    suspend fun insertFavoriteMovie(movie: Movie)

    suspend fun deleteFavoriteMovie(movie: Movie)
}