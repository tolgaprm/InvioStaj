package com.prmto.inviostaj.domain.usecase

import com.prmto.inviostaj.data.remote.dto.MovieDetail
import com.prmto.inviostaj.data.repository.MovieRepository
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val convertVoteCountToKFormatUseCase: ConvertVoteCountToKFormatUseCase,
    private val convertDateFormatUseCase: ConvertDateFormatUseCase,
    private val calculateRatingBarValueUseCase: CalculateRatingBarValueUseCase,
    private val convertRuntimeAsHourAndMinutesUseCase: ConvertRuntimeAsHourAndMinutesUseCase
) {
    suspend operator fun invoke(
        movieId: Int
    ): Result<MovieDetail> {
        val result = movieRepository.getMovieDetail(movieId)
        result.onSuccess { movieDetail ->
            return Result.success(
                MovieDetail(
                    id = movieDetail.id,
                    overview = movieDetail.overview,
                    posterPath = movieDetail.posterPath,
                    voteAverage = movieDetail.voteAverage,
                    voteCount = movieDetail.voteCount,
                    genres = movieDetail.genres,
                    releaseDate = convertDateFormatUseCase(inputDate = movieDetail.releaseDate),
                    runtime = movieDetail.runtime,
                    convertedRuntime = convertRuntimeAsHourAndMinutesUseCase(movieDetail.runtime),
                    ratingBarValue = calculateRatingBarValueUseCase(voteAverage = movieDetail.voteAverage),
                    originalTitle = movieDetail.originalTitle,
                    voteCountByString = convertVoteCountToKFormatUseCase(voteCount = movieDetail.voteCount),
                    imdbId = movieDetail.imdbId,
                    genresBySeparatedByComma = movieDetail.genres.map { it.name }.joinToString(", ")
                )
            )
        }.onFailure {
            return Result.failure(it)
        }
        return Result.failure(Exception())
    }
}