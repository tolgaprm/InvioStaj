package com.prmto.inviostaj.presentation.home.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.prmto.inviostaj.domain.model.Movie
import com.prmto.inviostaj.presentation.viewHolder.MovieViewHolder

class MoviePagingAdapter(
    private val onToggleFavoriteClicked: (Movie) -> Unit
) : PagingDataAdapter<Movie, MovieViewHolder>(MovieDiffer()) {


    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movieItem = getItem(position)
        movieItem?.let { movie ->
            holder.bind(
                movie = movie,
                context = holder.itemView.context,
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder.create(
            parent = parent,
            onToggleFavoriteClicked = onToggleFavoriteClicked
        )
    }
}

class MovieDiffer : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}