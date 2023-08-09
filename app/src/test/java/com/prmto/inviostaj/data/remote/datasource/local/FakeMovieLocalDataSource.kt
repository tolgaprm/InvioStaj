package com.prmto.inviostaj.data.remote.datasource.local

import com.prmto.inviostaj.constant.Resource
import com.prmto.inviostaj.data.local.datasource.MovieLocalDataSource
import com.prmto.inviostaj.data.remote.dto.Movie
import com.prmto.inviostaj.data.util.createResourceWithStatus

class FakeMovieLocalDataSource(
    private val isReturnSuccess: Boolean = true
) : MovieLocalDataSource {
    private val favoriteMovies: MutableList<Movie> = mutableListOf()
    override suspend fun getAllFavoriteMovies(): Resource<List<Movie>> {
        return createResourceWithStatus(isReturnSuccess, favoriteMovies)
    }

    override suspend fun insertFavoriteMovie(movie: Movie) {
        favoriteMovies.add(movie)
    }

    override suspend fun deleteFavoriteMovie(movie: Movie) {
        favoriteMovies.remove(movie)
    }
}