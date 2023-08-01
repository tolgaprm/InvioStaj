package com.prmto.inviostaj.domain.usecase

import com.prmto.inviostaj.domain.model.Movie
import com.prmto.inviostaj.domain.repository.MovieRepository
import javax.inject.Inject

class ToggleFavoriteMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movie: Movie) {
        if (movie.isFavorite) {
            movieRepository.deleteFavoriteMovie(movie)
        } else {
            movieRepository.insertFavoriteMovie(movie)
        }
    }
}