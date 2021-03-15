package com.mut_jaeryo.givmkeyword.ui.upload

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun setImageUrl(view: ImageView, imageUrl: String) {
    Glide.with(view.context)
            .load(imageUrl)
            .into(view)
}