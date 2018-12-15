package com.gis.repoimpl.domain.entitiy

data class User(val id: Int, val name: String) {

  companion object {
    val emptyUser = User(id = -1, name = "")
  }
}
