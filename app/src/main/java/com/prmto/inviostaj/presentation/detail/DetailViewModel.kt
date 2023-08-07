package com.prmto.inviostaj.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prmto.inviostaj.data.remote.dto.Movie
import com.prmto.inviostaj.domain.usecase.GetMovieDetailUseCase
import com.prmto.inviostaj.util.onError
import com.prmto.inviostaj.util.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getMovieDetailUseCase: GetMovieDetailUseCase
) : ViewModel() {
    private val _detailUiState = MutableStateFlow(DetailUiState())
    val detailUiState = _detailUiState.asStateFlow()

    init {
        savedStateHandle.get<Int>("movieId")?.let { movieId ->
            getMovieDetail(movieId = movieId)
        }
    }

    private fun getMovieDetail(movieId: Int) {
        viewModelScope.launch {
            _detailUiState.update { it.copy(isLoading = true) }
            val response = getMovieDetailUseCase(movieId = movieId)

            response
                .onSuccess { data ->
                    updateDetailStateOnSuccess(data)
                }
                .onError {
                    _detailUiState.update { it.copy(isLoading = false, isError = true) }
                }
        }
    }

    private fun updateDetailStateOnSuccess(data: Movie?) {
        _detailUiState.update {
            it.copy(
                isLoading = false,
                movieDetail = data,
                isError = false
            )
        }
    }
}