package com.prmto.inviostaj.domain.model

data class Movie(
    val id: Int,
    val overview: String,
    val title: String,
    val originalTitle: String,
    val posterPath: String?,
    val backdropPath: String?,
    var releaseDate: String?,
    val genreIds: List<Int>,
    val voteCount: Int,
    val genresBySeparatedByComma: String = "",
    val voteAverage: Double,
    val voteCountByString: String = "", // Format like 1000 k
)
