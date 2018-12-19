package com.gis.repoimpl.data.local.db

import androidx.room.TypeConverter
import com.bluelinelabs.logansquare.LoganSquare
import java.text.SimpleDateFormat
import java.util.*

class DbTypeConverters {

  @TypeConverter
  fun fromStringToList(value: String): List<String> =
    LoganSquare.parseList(value, String::class.java)

  @TypeConverter
  fun fromListToString(list: List<String>): String = LoganSquare.serialize(list)

  @TypeConverter
  fun fromStringToDate(dateString: String): Date {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    return dateFormat.parse(dateString)
  }

  @TypeConverter
  fun fromDateToString(date: Date): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    return dateFormat.format(date)
  }
}