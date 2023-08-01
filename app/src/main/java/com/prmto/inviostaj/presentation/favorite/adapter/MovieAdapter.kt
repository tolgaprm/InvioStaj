package com.prmto.inviostaj.presentation.favorite.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.prmto.inviostaj.domain.model.Movie
import com.prmto.inviostaj.presentation.home.adapter.MovieDiffer
import com.prmto.inviostaj.presentation.viewHolder.MovieViewHolder

class MovieAdapter(
    private val onToggleFavoriteClicked: (Movie) -> Unit
) : ListAdapter<Movie, MovieViewHolder>(MovieDiffer()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder.create(
            parent = parent,
            onToggleFavoriteClicked = onToggleFavoriteClicked
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movieItem = getItem(position)
        movieItem?.let { movie ->
            holder.bind(
                movie = movie,
                context = holder.itemView.context,
            )
        }
    }
}
