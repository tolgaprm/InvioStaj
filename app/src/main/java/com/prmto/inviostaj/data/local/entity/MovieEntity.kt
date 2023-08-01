package com.prmto.inviostaj.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.prmto.inviostaj.util.Constants.FAVORITE_MOVIE_ENTITY_TABLE_NAME

@Entity(tableName = FAVORITE_MOVIE_ENTITY_TABLE_NAME)
data class MovieEntity(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val overview: String,
    val originalTitle: String,
    val posterPath: String?,
    val backdropPath: String?,
    var releaseDate: String?,
    val genresBySeparatedByComma: String = "",
    val voteAverage: Double,
    val voteCountByString: String = "", // Format like 1000 k
)

