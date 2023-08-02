package com.prmto.inviostaj.presentation.viewHolder.listener

import com.prmto.inviostaj.data.remote.dto.Movie

interface MovieItemClickListener {
    fun onToggleFavoriteClicked(movie: Movie)
}