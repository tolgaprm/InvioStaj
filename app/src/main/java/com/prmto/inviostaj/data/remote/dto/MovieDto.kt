package com.prmto.inviostaj.data.remote.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.prmto.inviostaj.util.Constants
import com.squareup.moshi.Json

@Entity(tableName = Constants.FAVORITE_MOVIE_ENTITY_TABLE_NAME)
data class Movie(
    @PrimaryKey val id: Int,
    val overview: String,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "release_date") val releaseDate: String?,
    @Json(name = "genre_ids") val genreIds: List<Int>,
    @Json(name = "original_title") val originalTitle: String,
    @Json(name = "backdrop_path") val backdropPath: String?,
    @Json(name = "vote_count") val voteCount: Int,
    @Json(name = "vote_average") val voteAverage: Double,
    val genresBySeparatedByComma: String = "",
    val voteCountByString: String = "",
    val isFavorite: Boolean = false
)