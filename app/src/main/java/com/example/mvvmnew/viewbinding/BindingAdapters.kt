package com.example.mvvmnew.viewbinding

import android.view.View
import android.view.View.*
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.mvvmnew.R
import com.example.mvvmnew.extension.glideWithUrl
import com.example.mvvmnew.extension.glideCircle
import com.example.mvvmnew.extension.glideWithResId

@BindingAdapter("visibleOrGone")
fun View.visibleOrGone(visible: Boolean) {
    visibility = if (visible) VISIBLE else GONE
}

@BindingAdapter("opaqueOrTransparent")
fun View.opaqueOrTransparent(opaque: Boolean) {
    alpha = if (opaque) {
        1f
    } else {
        0.4f
    }
}

@BindingAdapter("clickableOrDisable")
fun View.clickableDisable(clickable: Boolean) {
    isClickable = clickable
    isFocusable = clickable
}

@BindingAdapter("visibleOrInvisible")
fun View.visibleOrInvisible(visible: Boolean) {
    visibility = if (visible) VISIBLE else INVISIBLE
}

@BindingAdapter("isSelected")
fun View.selectOrUnSelect(selected: Boolean) {
    this.isSelected = selected
}

@BindingAdapter("isEnabled")
fun View.enabledOrDisabled(enable: Boolean) {
    this.isEnabled = enable
}


@BindingAdapter("imageUrl")
internal fun ImageView.loadItemImage(imageUrl: String?) {
    when {
        (imageUrl.isNullOrEmpty()) -> glideWithResId(R.drawable.no_image)
        else -> glideWithUrl(url = imageUrl, errorResId = R.drawable.no_image)
    }
}

@BindingAdapter("imageUrlRound")
internal fun ImageView.loadItemImageCropRounded(imageUrl: String?) {
    when {
        (imageUrl.isNullOrEmpty()) -> glideCircle(R.drawable.no_image)
        else -> glideCircle(url = imageUrl, fallback = R.drawable.no_image)
    }
}
