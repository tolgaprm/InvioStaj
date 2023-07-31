package com.prmto.inviostaj.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import com.prmto.inviostaj.domain.model.Movie
import com.prmto.inviostaj.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTopRatedMoviePagingDataUseCase @Inject constructor(
    private val repository: MovieRepository,
    private val convertVoteCountToKFormatUseCase: ConvertVoteCountToKFormatUseCase,
    private val convertGenreListToSeparatedByCommaUseCase: ConvertGenreListToSeparatedByCommaUseCase
) {
    operator fun invoke(): Flow<PagingData<Movie>> {
        return repository.getTopRatedMovies().map { pagingData ->
            pagingData.map { movie ->
                movie.copy(
                    genresBySeparatedByComma = convertGenreListToSeparatedByCommaUseCase(
                        genreIds = movie.genreIds,
                        genreList = repository.getMovieGenreList().genres
                    ),
                    voteCountByString = convertVoteCountToKFormatUseCase(voteCount = movie.voteCount)
                )
            }
        }
    }
}