package com.gis.repoimpl.data.local.db

import android.content.Context
import androidx.room.Room

class GisAppTemplateDbProvider {

  companion object {

    fun createDb(context: Context): GisAppTemplateDb {
      return Room.databaseBuilder(context, GisAppTemplateDb::class.java, "GisAppTemplateDb")
        .allowMainThreadQueries()
        .build()
    }
  }
}