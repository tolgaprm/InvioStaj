package com.prmto.inviostaj.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.prmto.inviostaj.data.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM favorite_movie")
    fun getAllFavoriteMovies(): Flow<List<MovieEntity>>

    @Insert
    suspend fun insertFavoriteMovie(movieEntity: MovieEntity)

    @Delete
    suspend fun deleteFavoriteMovie(movieEntity: MovieEntity)
}