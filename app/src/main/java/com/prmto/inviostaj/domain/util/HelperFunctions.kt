package com.prmto.inviostaj.domain.util

import com.prmto.inviostaj.constant.Resource
import com.prmto.inviostaj.data.remote.dto.Movie

suspend fun Resource<List<Movie>>.updateMovieListWithFormattedInfo(
    convertGenreListWithComma: suspend (List<Int>) -> String,
    convertReleaseDate: (String?) -> String
): Resource<List<Movie>> {
    return data?.let { movieList ->
        val listOfMovie = movieList.map { movie ->
            movie.copy(
                genresBySeparatedByComma = convertGenreListWithComma(movie.genreIds),
                voteCountByString = MovieUtils.formatVoteCount(voteCount = movie.voteCount),
                releaseDate = convertReleaseDate(movie.releaseDate)
            )
        }
        Resource.Success(listOfMovie)
    } ?: Resource.Error(exception)
}