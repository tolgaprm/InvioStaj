package com.prmto.inviostaj.ui.bindingAdapters

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import com.prmto.inviostaj.R
import com.prmto.inviostaj.constant.ImageSize
import com.prmto.inviostaj.util.Constants.IMAGE_BASE_URL

@BindingAdapter("app:isVisible")
fun setVisibility(view: View, isVisible: Boolean) {
    view.visibility = if (isVisible) View.VISIBLE else View.GONE
}

@BindingAdapter(value = ["imageUrl", "imageSize"], requireAll = false)
fun loadImage(imageView: ImageView, url: String?, size: ImageSize?) {
    val imageSize = size ?: ImageSize.ORIGINAL

    url?.let {
        imageView.load(
            "$IMAGE_BASE_URL/${imageSize.path}/$url",
        ) {
            this.crossfade(true)
            this.placeholder(R.drawable.animate_loading)
        }
    }
}