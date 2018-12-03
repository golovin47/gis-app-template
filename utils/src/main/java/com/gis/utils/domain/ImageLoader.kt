package com.gis.utils.domain

import android.widget.ImageView

interface ImageLoader {

  fun loadImg(iv:ImageView, url: String)
}