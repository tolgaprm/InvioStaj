package com.prmto.inviostaj.domain.usecase

import com.prmto.inviostaj.data.remote.dto.Movie
import com.prmto.inviostaj.data.repository.MovieRepository
import com.prmto.inviostaj.domain.util.MovieUtils
import com.prmto.inviostaj.util.Resource
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val convertDateFormatUseCase: ConvertDateFormatUseCase
) {
    suspend operator fun invoke(
        movieId: Int
    ): Resource<Movie> {
        val response = movieRepository.getMovieDetail(movieId)
        return response.data?.let { movie ->
            val movieDetail = movie.copy(
                releaseDate = convertDateFormatUseCase(inputDate = movie.releaseDate),
                convertedRuntime = MovieUtils.convertRuntimeAsHourAndMinutes(runtime = movie.runtime),
                ratingBarValue = MovieUtils.calculateRatingBarValue(voteAverage = movie.voteAverage),
                voteCountByString = MovieUtils.formatVoteCount(voteCount = movie.voteCount),
                genresBySeparatedByComma = movie.genres.joinToString(", ") { it.name }
            )
            Resource.Success(movieDetail)
        } ?: Resource.Error(response.exception)
    }
}