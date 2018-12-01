package com.gis.repoimpl.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CatL(
  @PrimaryKey
  var id: String = "",
  var url: String = ""
)