package com.prmto.inviostaj.ui.home

import com.prmto.inviostaj.data.remote.dto.Movie

data class HomeUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    var currentPage: Int = 1,
    val movies: List<Movie> = emptyList(),
    val isLastPage: Boolean = false
)
