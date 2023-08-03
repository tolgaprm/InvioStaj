package com.prmto.inviostaj.domain.usecase

import com.prmto.inviostaj.data.remote.dto.Movie
import javax.inject.Inject

class UpdateMovieToIsFavoriteUseCase @Inject constructor() {
    operator fun invoke(
        favoriteMovies: List<Movie>,
        movies: List<Movie>
    ): List<Movie> {
        return movies.map { movie ->
            movie.copy(isFavorite = favoriteMovies.any { it.id == movie.id })
        }
    }
}