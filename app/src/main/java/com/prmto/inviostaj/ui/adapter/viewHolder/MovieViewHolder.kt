package com.prmto.inviostaj.ui.adapter.viewHolder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.prmto.inviostaj.data.remote.dto.Movie
import com.prmto.inviostaj.databinding.MovieItemBinding

class MovieViewHolder(
    val binding: MovieItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        movie: Movie
    ) {
        binding.movieItem = movie
        binding.executePendingBindings()
    }

    companion object {
        fun create(
            parent: ViewGroup
        ): MovieViewHolder {
            val binding =
                MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return MovieViewHolder(
                binding = binding
            )
        }
    }
}