package com.example.mvvmnew.extension

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

fun ImageView.glideWithUrl(
    url: String,
    placeHolder: Int? = null,
    fallback: Int? = null,
    errorResId: Int? = null
) {
    val builder = Glide.with(context).load(url)
    errorResId?.let {
        builder.error(it)
    }

    if (placeHolder != null) builder.placeholder(placeHolder)
    if (fallback != null) builder.fallback(fallback)

    builder.into(this)
}

fun ImageView.glideWithResId(
    resId: Int,
    placeHolder: Int? = null,
    fallback: Int? = null
) {
    val builder = Glide.with(context)
        .load(resId)

    if (placeHolder != null) builder.placeholder(placeHolder)
    if (fallback != null) builder.fallback(fallback)

    builder.into(this)
}

fun ImageView.glideCircle(
    url: String,
    placeHolder: Int? = null,
    fallback: Int? = null
) {
    val builder = Glide.with(context)
        .load(url)
        .circleCrop()

    if (placeHolder != null) builder.placeholder(placeHolder)
    if (fallback != null) builder.fallback(fallback)

    builder.into(this)
}

fun ImageView.glideCircle(
    resId: Int,
    placeHolder: Int? = null,
    fallback: Int? = null
) {
    val builder = Glide.with(context)
        .load(resId)
        .circleCrop()

    if (placeHolder != null) builder.placeholder(placeHolder)
    if (fallback != null) builder.fallback(fallback)

    builder.into(this)
}

fun ImageView.glideRounded(
    resId: Int,
    radiusDpi: Int,
    placeHolder: Int? = null,
    fallback: Int? = null,
    useFitCenter: Boolean = false
) {
    val px = radiusDpi.toFloat() * context.resources.displayMetrics.density

    val scaleType = if (useFitCenter) FitCenter() else CenterCrop()

    val builder = Glide.with(context)
        .load(resId)
        .transform(scaleType, RoundedCorners(px.toInt()))

    if (placeHolder != null) builder.placeholder(placeHolder)
    if (fallback != null) builder.fallback(fallback).error(fallback)

    builder.into(this)
}

fun ImageView.glideRounded(
    url: String,
    radiusDpi: Int,
    placeHolder: Int? = null,
    fallback: Int? = null,
    useFitCenter: Boolean = false
) {
    val px = radiusDpi.toFloat() * context.resources.displayMetrics.density

    val scaleType = if (useFitCenter) FitCenter() else CenterCrop()

    val builder = Glide.with(context)
        .load(url)
        .transform(scaleType, RoundedCorners(px.toInt()))

    if (placeHolder != null) builder.placeholder(placeHolder)
    if (fallback != null) builder.fallback(fallback).error(fallback)

    builder.into(this)
}