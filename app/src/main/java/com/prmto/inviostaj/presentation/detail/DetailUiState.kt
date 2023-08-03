package com.prmto.inviostaj.presentation.detail

import com.prmto.inviostaj.data.remote.dto.MovieDetail

data class DetailUiState(
    val isLoading: Boolean = false,
    val movieDetail: MovieDetail? = null,
    val error: String = "",
)
