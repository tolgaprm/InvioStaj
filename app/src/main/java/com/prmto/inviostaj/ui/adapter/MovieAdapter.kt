package com.prmto.inviostaj.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.prmto.inviostaj.data.remote.dto.Movie
import com.prmto.inviostaj.ui.adapter.viewHolder.MovieViewHolder
import com.prmto.inviostaj.ui.adapter.viewHolder.listener.MovieItemClickListener

class MovieAdapter(
    private val movieItemClickListener: MovieItemClickListener
) : ListAdapter<Movie, MovieViewHolder>(MovieDiffer()), MovieItemClickListener {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder.create(
            parent = parent
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movieItem = getItem(position)
        holder.binding.movieItemClickListener = this
        movieItem?.let { movie ->
            holder.bind(
                movie = movie
            )
        }
    }

    override fun onToggleFavoriteClicked(movie: Movie) {
        movieItemClickListener.onToggleFavoriteClicked(movie)
    }

    override fun onMovieClicked(movieId: Int) {
        movieItemClickListener.onMovieClicked(movieId)
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