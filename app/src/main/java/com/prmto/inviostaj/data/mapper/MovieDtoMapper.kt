package com.prmto.inviostaj.data.mapper

import com.prmto.inviostaj.data.remote.dto.MovieDto
import com.prmto.inviostaj.domain.model.Movie


fun MovieDto.toMovie(): Movie {
    return Movie(
        id = id,
        overview = overview,
        title = title,
        originalTitle = originalTitle,
        posterPath = posterPath,
        releaseDate = releaseDate,
        genreIds = genreIds,
        voteCount = voteCount,
        voteAverage = voteAverage
    )
}