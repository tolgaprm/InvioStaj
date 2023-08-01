package com.prmto.inviostaj.data.mapper

import com.prmto.inviostaj.data.local.entity.MovieEntity
import com.prmto.inviostaj.domain.model.Movie

fun MovieEntity.toMovie(): Movie {
    return Movie(
        id = id,
        overview = overview,
        originalTitle = originalTitle,
        posterPath = posterPath,
        backdropPath = backdropPath,
        releaseDate = releaseDate,
        genresBySeparatedByComma = genresBySeparatedByComma,
        voteAverage = voteAverage,
        voteCountByString = voteCountByString,
        title = originalTitle,
        genreIds = listOf(),
        voteCount = 0
    )
}

fun Movie.toMovieEntity(): MovieEntity {
    return MovieEntity(
        id = id,
        overview = overview,
        originalTitle = originalTitle,
        posterPath = posterPath,
        backdropPath = backdropPath,
        releaseDate = releaseDate,
        genresBySeparatedByComma = genresBySeparatedByComma,
        voteAverage = voteAverage,
        voteCountByString = voteCountByString
    )
}