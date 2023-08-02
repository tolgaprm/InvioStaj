package com.prmto.inviostaj.data

import com.prmto.inviostaj.data.remote.dto.GenreList
import com.prmto.inviostaj.data.remote.dto.Movie
import com.prmto.inviostaj.data.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeMovieRepository : MovieRepository {
    override suspend fun getTopRatedMovies(page: Int): List<Movie> {
        return emptyList()
    }

    override suspend fun getMovieGenreList(): GenreList {
        return GenreList(emptyList())
    }

    override fun getFavoriteMovies(): Flow<List<Movie>> {
        return flow {
            emit(
                listOf(
                    Movie(
                        id = 1,
                        overview = "Overview",
                        posterPath = "PosterPath",
                        backdropPath = "BackdropPath",
                        voteAverage = 1.0,
                        voteCount = 1,
                        releaseDate = "ReleaseDate",
                        genreIds = emptyList(),
                        originalTitle = "OriginalTitle",
                        genresBySeparatedByComma = "GenresBySeparatedByComma",
                        isFavorite = true
                    )
                )
            )
        }
    }

    override suspend fun insertFavoriteMovie(movie: Movie) {
        return
    }

    override suspend fun deleteFavoriteMovie(movie: Movie) {
        return
    }
}