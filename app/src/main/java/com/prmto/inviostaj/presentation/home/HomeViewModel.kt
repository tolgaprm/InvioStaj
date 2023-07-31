package com.prmto.inviostaj.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.prmto.inviostaj.domain.usecase.GetTopRatedMoviePagingDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getTopRatedMoviePagingDataUseCase: GetTopRatedMoviePagingDataUseCase
) : ViewModel() {
    val topRatedMoviesPagingData = getTopRatedMoviePagingDataUseCase().cachedIn(viewModelScope)
}