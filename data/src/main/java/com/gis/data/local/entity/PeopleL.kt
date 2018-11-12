package com.gis.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PeopleL(
    @PrimaryKey
    var id: Int = 0,
    var name: String = "",
    var metAt: String = "",
    var contact: String = "",
    var email: String = "",
    var facebook: String = "",
    var twitter: String = ""
)