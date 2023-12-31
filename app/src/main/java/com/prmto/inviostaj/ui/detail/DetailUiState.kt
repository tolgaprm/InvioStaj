package com.prmto.inviostaj.ui.detail

import com.prmto.inviostaj.data.remote.dto.Movie

data class DetailUiState(
    val isLoading: Boolean = false,
    val movieDetail: Movie? = null,
    val isError: Boolean = false
)