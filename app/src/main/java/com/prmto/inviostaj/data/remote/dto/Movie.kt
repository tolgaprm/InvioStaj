package com.prmto.inviostaj.data.remote.dto

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.prmto.inviostaj.util.Constants
import com.squareup.moshi.Json

@Entity(tableName = Constants.FAVORITE_MOVIE_ENTITY_TABLE_NAME)
data class Movie(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val overview: String,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "release_date") val releaseDate: String?,
    @Json(name = "original_title") val originalTitle: String,
    @Json(name = "backdrop_path") val backdropPath: String?,
    @Json(name = "vote_average") val voteAverage: Double,
    val genresBySeparatedByComma: String = "",
    val voteCountByString: String = "", // Format like 1000 -> 1K, 18000 -> 18K
    val isFavorite: Boolean = false,
    @Ignore @Json(name = "vote_count") val voteCount: Int = 0,
    @Ignore @Json(name = "genre_ids") val genreIds: List<Int> = emptyList(),
    @Ignore @Json(name = "imdb_id") val imdbId: String?,
    @Ignore val runtime: Int = 0,
    @Ignore val genres: List<Genre> = emptyList(),
    @Ignore val ratingBarValue: Float = 0.0f,
    @Ignore var convertedRuntime: Map<String, String> = emptyMap()
) {
    constructor(
        id: Int,
        overview: String,
        posterPath: String?,
        releaseDate: String?,
        originalTitle: String,
        backdropPath: String?,
        voteAverage: Double,
        genresBySeparatedByComma: String,
        voteCountByString: String,
        isFavorite: Boolean,
        voteCount: Int,
        genreIds: List<Int>
    ) : this(
        id = id,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        originalTitle = originalTitle,
        backdropPath = backdropPath,
        voteAverage = voteAverage,
        genresBySeparatedByComma = genresBySeparatedByComma,
        voteCountByString = voteCountByString,
        isFavorite = isFavorite,
        imdbId = "",
        voteCount = voteCount,
        genreIds = genreIds
    )

    constructor(
        id: Int,
        overview: String,
        posterPath: String?,
        releaseDate: String?,
        originalTitle: String,
        backdropPath: String?,
        voteAverage: Double,
        genresBySeparatedByComma: String,
        voteCountByString: String,
        isFavorite: Boolean
    ) : this(
        id = id,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        originalTitle = originalTitle,
        backdropPath = backdropPath,
        voteAverage = voteAverage,
        genresBySeparatedByComma = genresBySeparatedByComma,
        voteCountByString = voteCountByString,
        isFavorite = isFavorite,
        imdbId = ""
    )
}