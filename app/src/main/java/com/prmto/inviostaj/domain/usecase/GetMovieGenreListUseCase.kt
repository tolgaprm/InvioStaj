package com.prmto.inviostaj.domain.usecase

import com.prmto.inviostaj.domain.model.Genre
import com.prmto.inviostaj.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovieGenreListUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(): List<Genre> {
        return repository.getMovieGenreList().genres
    }
}