package com.prmto.inviostaj.domain.usecase

import com.prmto.inviostaj.data.repository.MovieRepository
import javax.inject.Inject

class ConvertMovieGenreListToSeparatedByCommaUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(
        genreIds: List<Int>
    ): String {
        if (genreIds.isEmpty()) {
            return ""
        }
        val genreList = repository.getMovieGenreList().data?.genres ?: return ""
        val matchingGenres = genreList.filter { it.id in genreIds }.map { it.name }
        return matchingGenres.joinToString(", ")
    }
}