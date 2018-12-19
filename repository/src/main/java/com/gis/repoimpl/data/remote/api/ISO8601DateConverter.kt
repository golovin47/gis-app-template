package com.gis.repoimpl.data.remote.api

import com.bluelinelabs.logansquare.typeconverters.DateTypeConverter
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class ISO8601DateConverter(
  private val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
) : DateTypeConverter() {

  override fun getDateFormat(): DateFormat {
    return dateFormat
  }
}