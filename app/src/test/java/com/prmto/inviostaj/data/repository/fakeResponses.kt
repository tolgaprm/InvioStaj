package com.prmto.inviostaj.data.repository

import com.prmto.inviostaj.data.remote.dto.Genre
import com.prmto.inviostaj.data.remote.dto.GenreList
import com.prmto.inviostaj.data.remote.dto.Movie

class FakeResponse {
    companion object {
        val oppenheimerMovie =
            Movie(
                id = 872585,
                overview = "The story of J. Robert Oppenheimer’s role in the development of the atomic bomb during World War II.",
                posterPath = "/8Gxv8gSFCU0XGDykEGv7zR1n2ua.jpg",
                backdropPath = "/fm6KqXpk3M2HVveHwCrBSSBaO0V.jpg",
                voteAverage = 8.303,
                releaseDate = "2023-07-19",
                isFavorite = false,
                originalTitle = "Oppenheimer",
                voteCount = 1581,
                genreIds = listOf(18, 36),
                imdbId = null
            )
        val godFatherMovie = Movie(
            id = 238,
            overview = "Spanning the years 1945 to 1955, a chronicle of the fictional Italian-American Corleone crime family. When organized crime family patriarch, Vito Corleone barely survives an attempt on his life, his youngest son, Michael steps in to take care of the would-be killers, launching a campaign of bloody revenge.",
            posterPath = "/3bhkrj58Vtu7enYsRolD1fZdja1.jpg",
            backdropPath = "/rSPw7tgCH9c6NqICZef4kZjFOQ5.jpg",
            voteAverage = 8.7,
            releaseDate = "1972-03-14",
            isFavorite = false,
            originalTitle = "The Godfather",
            genreIds = listOf(18, 80),
            voteCount = 18378,
            imdbId = null
        )

        val responseListOfMovies = listOf(
            oppenheimerMovie,
            godFatherMovie
        )

        val moviesWithFavoriteStatus = listOf(
            oppenheimerMovie.copy(isFavorite = true),
            godFatherMovie.copy(isFavorite = false)
        )

        val favoriteMovies = listOf(oppenheimerMovie)

        val responseMovieDetail = Movie(
            id = 872585,
            backdropPath = "/fm6KqXpk3M2HVveHwCrBSSBaO0V.jpg",
            genres = listOf(
                Genre(
                    id = 18,
                    name = "Drama"
                ),
                Genre(
                    id = 36,
                    name = "History"
                )
            ),
            imdbId = "tt15398776",
            originalTitle = "Oppenheimer",
            overview = "The story of J. Robert Oppenheimer’s role in the development of the atomic bomb during World War II.",
            posterPath = "/8Gxv8gSFCU0XGDykEGv7zR1n2ua.jpg",
            releaseDate = "2023-07-19",
            runtime = 181,
            voteAverage = 8.303,
            voteCount = 1581
        )

        val movieDetailId = 872585

        val oppenheimerQuery = "oppenheimer"

        val responseGenreList =
            GenreList(
                genres = listOf(
                    Genre(
                        id = 28,
                        name = "Action"
                    ),
                    Genre(
                        id = 12,
                        name = "Adventure"
                    ),
                    Genre(
                        id = 16,
                        name = "Animation"
                    ),
                    Genre(
                        id = 35,
                        name = "Comedy"
                    ),
                    Genre(
                        id = 80,
                        name = "Crime"
                    ),
                    Genre(
                        id = 99,
                        name = "Documentary"
                    ),
                    Genre(
                        id = 18,
                        name = "Drama"
                    ),
                    Genre(
                        id = 10751,
                        name = "Family"
                    ),
                    Genre(
                        id = 14,
                        name = "Fantasy"
                    ),
                    Genre(
                        id = 36,
                        name = "History"
                    ),
                    Genre(
                        id = 27,
                        name = "Horror"
                    ),
                    Genre(
                        id = 10402,
                        name = "Music"
                    ),
                    Genre(
                        id = 9648,
                        name = "Mystery"
                    ),
                    Genre(
                        id = 10749,
                        name = "Romance"
                    ),
                    Genre(
                        id = 878,
                        name = "Science Fiction"
                    ),
                    Genre(
                        id = 10770,
                        name = "TV Movie"
                    ),
                    Genre(
                        id = 10752,
                        name = "War"
                    ),
                    Genre(
                        id = 53,
                        name = "Thriller"
                    ),
                    Genre(
                        id = 37,
                        name = "Western"
                    )
                )
            )

    }
}