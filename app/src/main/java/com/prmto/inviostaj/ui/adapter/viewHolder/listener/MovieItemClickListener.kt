package com.prmto.inviostaj.ui.adapter.viewHolder.listener

import com.prmto.inviostaj.data.remote.dto.Movie

interface MovieItemClickListener {
    fun onToggleFavoriteClicked(movie: Movie)

    fun onMovieClicked(movieId: Int)
}