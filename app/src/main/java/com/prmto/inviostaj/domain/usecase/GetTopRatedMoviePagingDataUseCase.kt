package com.prmto.inviostaj.domain.usecase

import com.prmto.inviostaj.data.remote.dto.Movie
import com.prmto.inviostaj.data.repository.MovieRepository
import com.prmto.inviostaj.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTopRatedMoviePagingDataUseCase @Inject constructor(
    private val repository: MovieRepository,
    private val convertVoteCountToKFormatUseCase: ConvertVoteCountToKFormatUseCase,
    private val convertGenreListToSeparatedByCommaUseCase: ConvertGenreListToSeparatedByCommaUseCase,
    private val convertDateFormatUseCase: ConvertDateFormatUseCase
) {
    suspend operator fun invoke(page: Int): Flow<Resource<List<Movie>>> {

        return flow {
            try {
                emit(Resource.Loading())
                val resource = combine(
                    repository.getTopRatedMovies(page = page),
                    repository.getFavoriteMovies()
                ) { movies, favoriteMovies ->
                    movies.map { movie ->
                        movie.copy(
                            genresBySeparatedByComma = convertGenreListToSeparatedByCommaUseCase(
                                genreIds = movie.genreIds,
                                genreList = repository.getMovieGenreList().genres
                            ),
                            voteCountByString = convertVoteCountToKFormatUseCase(voteCount = movie.voteCount),
                            releaseDate = convertDateFormatUseCase(inputDate = movie.releaseDate),
                            isFavorite = favoriteMovies.any { it.id == movie.id }
                        )
                    }
                }
                emit(Resource.Success(data = resource.first()))
            } catch (e: Exception) {
                emit(Resource.Error(e.localizedMessage ?: ""))
            }
        }
    }
}
