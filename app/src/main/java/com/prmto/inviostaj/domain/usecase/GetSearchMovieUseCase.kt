package com.prmto.inviostaj.domain.usecase

import com.prmto.inviostaj.data.remote.dto.Movie
import com.prmto.inviostaj.data.repository.MovieRepository
import com.prmto.inviostaj.domain.util.updateMovieListWithFormattedInfo
import com.prmto.inviostaj.util.Resource
import javax.inject.Inject

class GetSearchMovieUseCase @Inject constructor(
    private val repository: MovieRepository,
    private val convertDateFormatUseCase: ConvertDateFormatUseCase,
    private val convertMovieGenreListToSeparatedByCommaUseCase: ConvertMovieGenreListToSeparatedByCommaUseCase
) {
    suspend operator fun invoke(query: String, page: Int): Resource<List<Movie>> {
        val response = repository.getSearchMovies(query, page)
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