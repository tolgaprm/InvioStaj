package com.prmto.inviostaj.presentation.bindingAdapters

import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import coil.load
import com.prmto.inviostaj.R
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
