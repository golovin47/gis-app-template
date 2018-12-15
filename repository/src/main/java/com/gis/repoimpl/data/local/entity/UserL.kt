package com.gis.repoimpl.data.local.entity

data class UserL(val id: Int, val name: String) {
  override fun toString(): String {
    return "$id,$name"
  }
}