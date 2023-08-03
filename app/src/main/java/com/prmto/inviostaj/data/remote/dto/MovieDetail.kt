package com.prmto.inviostaj.data.remote.dto

import com.squareup.moshi.Json

data class MovieDetail(
    val id: Int,
    val overview: String,
    @Json(name = "poster_path") val posterPath: String?,
    @Json(name = "release_date") val releaseDate: String?,
    val genres: List<Genre>,
    val runtime: Int,
    @Json(name = "imdb_id") val imdbId: String?,
    @Json(name = "original_title") val originalTitle: String,
    @Json(name = "vote_count") val voteCount: Int,
    @Json(name = "vote_average") val voteAverage: Double,
    val genresBySeparatedByComma: String = "",
    val voteCountByString: String = "",
    val ratingBarValue: Float = 0.0f,
    var convertedRuntime: Map<String, String> = emptyMap(),
) {
}
