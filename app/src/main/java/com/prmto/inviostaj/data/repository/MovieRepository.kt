package com.prmto.inviostaj.data.repository

import androidx.paging.PagingData
import com.prmto.inviostaj.data.remote.dto.GenreList
import com.prmto.inviostaj.data.remote.dto.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getTopRatedMovies(): Flow<PagingData<Movie>>

    suspend fun getMovieGenreList(): GenreList

    fun getFavoriteMovies(): Flow<List<Movie>>

    suspend fun insertFavoriteMovie(movie: Movie)

    suspend fun deleteFavoriteMovie(movie: Movie)
}