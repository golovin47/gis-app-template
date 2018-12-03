package com.gis.utils.data

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.gis.utils.domain.ImageLoader

object GlideImageLoader : ImageLoader {

  override fun loadImg(iv: ImageView, url: String) {
    Glide.with(iv)
      .load(url)
      .into(iv)
  }
}