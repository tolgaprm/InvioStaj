package com.prmto.inviostaj.data.remote.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.prmto.inviostaj.data.mapper.toMovie
import com.prmto.inviostaj.domain.model.Movie
import javax.inject.Inject

class MoviePagingSource @Inject constructor(
    private val movieRemoteDataSource: MovieRemoteDataSource
) : PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val nextPage = params.key ?: 1
        return try {
            val response = movieRemoteDataSource.getTopRatedMovies(page = nextPage)

            val prevKey = if (nextPage == 1) null else nextPage - 1
            val nextKey = if (nextPage < response.totalPages) response.page.plus(1) else null

            LoadResult.Page(
                data = response.results.map { it.toMovie() },
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(throwable = e)
        }
    }
}