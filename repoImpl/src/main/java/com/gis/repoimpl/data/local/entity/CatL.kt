package com.gis.repoimpl.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CatL(
  var id: String = "",
  var url: String = ""
){

  @PrimaryKey(autoGenerate = true)
  var dbId: Int = 0
}