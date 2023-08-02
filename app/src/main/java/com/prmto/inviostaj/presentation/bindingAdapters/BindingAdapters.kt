package com.prmto.inviostaj.presentation.bindingAdapters

import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.prmto.inviostaj.R
import com.prmto.inviostaj.data.remote.dto.Movie
import com.prmto.inviostaj.presentation.favorite.adapter.MovieAdapter
import com.prmto.inviostaj.util.Constants.IMAGE_BASE_URL
import com.prmto.inviostaj.util.ImageSize


@BindingAdapter("app:isVisible")
fun setVisibility(view: View, isVisible: Boolean) {
    view.isVisible = isVisible
}

@BindingAdapter(value = ["imageUrl", "imageSize"], requireAll = false)
fun loadImage(imageView: ImageView, url: String, size: ImageSize?) {
    val imageSize = size ?: ImageSize.ORIGINAL

    imageView.load(
        "$IMAGE_BASE_URL/${imageSize.path}/$url",
    ) {
        this.crossfade(true)
        this.placeholder(R.drawable.animate_loading)
    }
}

@BindingAdapter("submitMovieList")
fun submitList(rv: RecyclerView, list: List<Movie>) {
    if (list.isNotEmpty()) {
        val movieAdapter = rv.adapter as? MovieAdapter

        movieAdapter?.let {
            movieAdapter.submitList(list)
        }
    }
}