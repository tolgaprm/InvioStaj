package com.prmto.inviostaj.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.prmto.inviostaj.domain.usecase.GetTopRatedMoviePagingDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getTopRatedMoviePagingDataUseCase: GetTopRatedMoviePagingDataUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeUiState())
    val state = _state.asStateFlow()

    val topRatedMoviesPagingData = getTopRatedMoviePagingDataUseCase().cachedIn(viewModelScope)

    fun updateErrorState(isError: Boolean) {
        _state.update { it.copy(isError = isError) }
    }

    fun updateLoadingState(isLoading: Boolean) {
        _state.update { it.copy(isLoading = isLoading) }
    }
}