package com.prmto.inviostaj.domain.repository

import androidx.paging.PagingData
import com.prmto.inviostaj.domain.model.GenreList
import com.prmto.inviostaj.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getTopRatedMovies(): Flow<PagingData<Movie>>

    suspend fun getMovieGenreList(): GenreList

    fun getFavoriteMovies(): Flow<List<Movie>>

    suspend fun insertFavoriteMovie(movie: Movie)

    suspend fun deleteFavoriteMovie(movie: Movie)
}