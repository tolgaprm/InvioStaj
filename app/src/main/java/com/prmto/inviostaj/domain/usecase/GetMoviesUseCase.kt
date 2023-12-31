package com.prmto.inviostaj.domain.usecase

import com.prmto.inviostaj.constant.Resource
import com.prmto.inviostaj.data.remote.dto.Movie
import com.prmto.inviostaj.data.repository.MovieRepository
import com.prmto.inviostaj.domain.util.MovieUtils
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    private val repository: MovieRepository,
    private val convertDateFormatUseCase: ConvertDateFormatUseCase,
    private val convertMovieGenreListToSeparatedByCommaUseCase: ConvertMovieGenreListToSeparatedByCommaUseCase
) {
    suspend operator fun invoke(query: String? = null, page: Int): Resource<List<Movie>> {
        val response = if (query.isNullOrEmpty()) {
            repository.getTopRatedMovies(page = page)
        } else {
            repository.getSearchMovies(query = query, page = page)
        }
        return response.data?.let { movieList ->
            val listOfMovie = movieList.map { movie ->
                movie.copy(
                    genresBySeparatedByComma = convertMovieGenreListToSeparatedByCommaUseCase(movie.genreIds),
                    voteCountByString = MovieUtils.formatVoteCount(voteCount = movie.voteCount),
                    releaseDate = convertDateFormatUseCase(movie.releaseDate)
                )
            }
            Resource.Success(listOfMovie)
        } ?: Resource.Error(response.exception)
    }
}