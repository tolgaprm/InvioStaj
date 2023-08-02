package com.prmto.inviostaj.domain.usecase

import com.prmto.inviostaj.data.remote.dto.Movie
import com.prmto.inviostaj.data.repository.MovieRepository
import javax.inject.Inject

class ToggleFavoriteMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movie: Movie) {
        if (movie.isFavorite) {
            movieRepository.deleteFavoriteMovie(movie.copy(isFavorite = false))
        } else {
            movieRepository.insertFavoriteMovie(movie.copy(isFavorite = true))
        }
    }
}