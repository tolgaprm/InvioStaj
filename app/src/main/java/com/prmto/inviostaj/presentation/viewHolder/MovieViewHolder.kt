package com.prmto.inviostaj.presentation.viewHolder

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.prmto.inviostaj.R
import com.prmto.inviostaj.data.remote.api.ImageApi
import com.prmto.inviostaj.databinding.MovieItemBinding
import com.prmto.inviostaj.domain.model.Movie

class MovieViewHolder(
    private val binding: MovieItemBinding,
    private val onToggleFavoriteClicked: (Movie) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        movie: Movie,
        context: Context,
    ) {
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

        toggleFavorite(isFavorite = movie.isFavorite)

        binding.ibtnMovieItemFavorite.setOnClickListener {
            onToggleFavoriteClicked(movie)
        }
    }

    private fun toggleFavorite(isFavorite: Boolean) {
        binding.ibtnMovieItemFavorite.setImageResource(
            if (isFavorite) R.drawable.ic_favorite_black else R.drawable.ic_favorite_border_black
        )
    }

    companion object {
        fun create(
            parent: ViewGroup,
            onToggleFavoriteClicked: (Movie) -> Unit
        ): MovieViewHolder {
            val binding =
                MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return MovieViewHolder(
                binding = binding,
                onToggleFavoriteClicked = onToggleFavoriteClicked
            )
        }
    }
}