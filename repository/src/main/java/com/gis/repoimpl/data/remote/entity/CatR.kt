package com.gis.repoimpl.data.remote.entity

import com.bluelinelabs.logansquare.annotation.JsonField
import com.bluelinelabs.logansquare.annotation.JsonObject

@JsonObject
data class CatR(
  @JsonField(name = ["id"]) var id: String? = "",
  @JsonField(name = ["url"]) var url: String? = ""
)