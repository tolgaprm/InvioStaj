package com.prmto.inviostaj.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.prmto.inviostaj.data.remote.dto.Movie

@Dao
interface MovieDao {
    @Query("SELECT * FROM favorite_movie")
    suspend fun getAllFavoriteMovies(): List<Movie>

    @Insert
    suspend fun insertFavoriteMovie(movie: Movie)

    @Delete
    suspend fun deleteFavoriteMovie(movie: Movie)
}