package com.prmto.inviostaj.domain.usecase

import com.prmto.inviostaj.data.remote.dto.Movie
import com.prmto.inviostaj.data.repository.MovieRepository
import com.prmto.inviostaj.domain.util.updateMovieListWithFormattedInfo
import com.prmto.inviostaj.util.Resource
import javax.inject.Inject

class GetTopRatedMoviePagingDataUseCase @Inject constructor(
    private val repository: MovieRepository,
    private val convertMovieGenreListToSeparatedByCommaUseCase: ConvertMovieGenreListToSeparatedByCommaUseCase,
    private val convertDateFormatUseCase: ConvertDateFormatUseCase
) {
    suspend operator fun invoke(page: Int): Resource<List<Movie>> {
        val response = repository.getTopRatedMovies(page = page)
        return response.updateMovieListWithFormattedInfo(
            convertGenreListWithComma = { genreIds ->
                convertMovieGenreListToSeparatedByCommaUseCase(genreIds = genreIds)
            },
            convertReleaseDate = { releaseDate ->
                convertDateFormatUseCase(inputDate = releaseDate)
            }
        )
    }
}