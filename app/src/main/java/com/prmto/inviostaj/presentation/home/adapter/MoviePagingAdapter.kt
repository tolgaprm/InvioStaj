package com.prmto.inviostaj.presentation.home.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.prmto.inviostaj.data.remote.dto.Movie
import com.prmto.inviostaj.presentation.viewHolder.MovieViewHolder
import com.prmto.inviostaj.presentation.viewHolder.listener.MovieItemClickListener

class MoviePagingAdapter(
    private val onToggleFavoriteClick: (Movie) -> Unit
) : PagingDataAdapter<Movie, MovieViewHolder>(MovieDiffer()), MovieItemClickListener {


    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movieItem = getItem(position)
        holder.binding.toggleFavoriteClickListener = this
        movieItem?.let { movie ->
            holder.bind(
                movie = movie
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder.create(
            parent = parent
        )
    }

    override fun onToggleFavoriteClicked(movie: Movie) {
        onToggleFavoriteClick(movie)
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