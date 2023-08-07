package com.prmto.inviostaj.ui.favorite

import com.prmto.inviostaj.data.remote.dto.Movie

data class FavoriteUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val favoriteMovies: List<Movie> = emptyList()
)