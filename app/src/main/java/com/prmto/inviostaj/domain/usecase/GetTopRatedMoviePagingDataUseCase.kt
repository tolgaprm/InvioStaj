package com.prmto.inviostaj.domain.usecase

import com.prmto.inviostaj.data.remote.dto.Movie
import com.prmto.inviostaj.data.repository.MovieRepository
import com.prmto.inviostaj.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class GetTopRatedMoviePagingDataUseCase @Inject constructor(
    private val repository: MovieRepository,
    private val convertVoteCountToKFormatUseCase: ConvertVoteCountToKFormatUseCase,
    private val convertGenreListToSeparatedByCommaUseCase: ConvertGenreListToSeparatedByCommaUseCase,
    private val convertDateFormatUseCase: ConvertDateFormatUseCase
) {
    suspend operator fun invoke(page: Int): Flow<Resource<List<Movie>>> {
        return repository.getTopRatedMovies(page = page).onEach { resource ->
            if (resource is Resource.Success) {
                resource.data?.map { movie ->
                    movie.copy(
                        genresBySeparatedByComma =
                        convertGenreListToSeparatedByCommaUseCase(
                            genreIds = movie.genreIds,
                            genreList = repository.getMovieGenreList().genres
                        ),
                        voteCountByString =
                        convertVoteCountToKFormatUseCase(voteCount = movie.voteCount),
                        releaseDate =
                        convertDateFormatUseCase(inputDate = movie.releaseDate),
                    )
                }
            }
        }
    }
}