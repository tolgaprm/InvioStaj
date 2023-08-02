package com.prmto.inviostaj.presentation.favorite

import com.prmto.inviostaj.data.remote.dto.Movie


data class FavoriteUiState(
    val isLoading: Boolean = false,
    val favoriteMovies: List<Movie> = emptyList()
)
