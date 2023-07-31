package com.prmto.inviostaj.util

import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState

fun CombinedLoadStates.isErrorWithLoadState(): LoadState.Error? {
    return when {
        this.source.refresh is LoadState.Error -> this.source.refresh as LoadState.Error
        this.refresh is LoadState.Error -> this.refresh as LoadState.Error
        this.append is LoadState.Error -> this.append as LoadState.Error
        this.prepend is LoadState.Error -> this.prepend as LoadState.Error
        else -> null
    }
}

fun CombinedLoadStates.isLoading(): Boolean {
    return when (this.refresh) {
        is LoadState.Loading -> true
        is LoadState.NotLoading -> false
        else -> false
    }
}