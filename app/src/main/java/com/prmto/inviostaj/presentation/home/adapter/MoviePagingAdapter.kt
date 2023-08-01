package com.prmto.inviostaj.presentation.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.prmto.inviostaj.R
import com.prmto.inviostaj.data.remote.api.ImageApi
import com.prmto.inviostaj.databinding.MovieItemBinding
import com.prmto.inviostaj.domain.model.Movie

class MoviePagingAdapter :
    PagingDataAdapter<Movie, MoviePagingAdapter.MovieViewHolder>(MovieDiffer()) {

    class MovieViewHolder(
        private val binding: MovieItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie, context: Context) {
            binding.tvMovieItemName.text = movie.originalTitle
            binding.tvMovieItemReleaseDate.text = movie.releaseDate
            binding.tvMovieItemOverview.text = movie.overview
            binding.imvMovieItemBackdrop.load(
                ImageApi.getImage(imageUrl = movie.backdropPath)
            )
            binding.imvMovieItemPoster.load(
                ImageApi.getImage(imageUrl = movie.posterPath)
            )
            binding.tvMovieItemVoteAverage.text = context.getString(
                R.string.voteAverage,
                movie.voteAverage.toString(),
                movie.voteCountByString
            )
            binding.tvMovieItemGenre.text = movie.genresBySeparatedByComma
            binding.tvMovieItemReleaseDate.text = movie.releaseDate
        }

        companion object {
            fun create(
                parent: ViewGroup
            ): MovieViewHolder {
                val binding =
                    MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return MovieViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movieItem = getItem(position)
        movieItem?.let { movie ->
            holder.bind(movie = movie, context = holder.itemView.context)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder.create(parent)
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