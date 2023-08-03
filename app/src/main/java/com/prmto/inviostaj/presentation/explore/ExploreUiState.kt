package com.prmto.inviostaj.presentation.explore

import com.prmto.inviostaj.data.remote.dto.Movie

data class ExploreUiState(
    val isLoading: Boolean = false,
    val movies: List<Movie> = emptyList(),
    var currentPage: Int = 1,
    val isLastPage: Boolean = false,
    val isError: Boolean = false,
    val query: String = ""
)
