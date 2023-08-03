package com.prmto.inviostaj.domain.usecase

import com.prmto.inviostaj.data.remote.dto.Movie
import com.prmto.inviostaj.data.repository.MovieRepository
import com.prmto.inviostaj.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class GetSearchMovieUseCase @Inject constructor(
    private val repository: MovieRepository,
    private val convertDateFormatUseCase: ConvertDateFormatUseCase,
    private val voteCountToKFormatUseCase: ConvertVoteCountToKFormatUseCase,
    private val convertGenreListToSeparatedByCommaUseCase: ConvertGenreListToSeparatedByCommaUseCase
) {

    operator fun invoke(query: String, page: Int): Flow<Resource<List<Movie>>> {
        return repository.getSearchMovies(query, page).onEach {
            if (it is Resource.Success) {
                it.data?.map { movie ->
                    movie.copy(
                        releaseDate = convertDateFormatUseCase(movie.releaseDate),
                        voteCountByString = voteCountToKFormatUseCase(movie.voteCount),
                        genresBySeparatedByComma = convertGenreListToSeparatedByCommaUseCase(
                            movie.genreIds,
                            repository.getMovieGenreList().genres
                        ),
                    )
                }
            }
        }
    }
}