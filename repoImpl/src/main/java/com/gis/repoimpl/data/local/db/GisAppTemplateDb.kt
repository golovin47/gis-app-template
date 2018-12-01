package com.gis.repoimpl.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gis.repoimpl.data.local.entity.CatL

@Database(entities = [CatL::class], version = 1, exportSchema = false)
abstract class GisAppTemplateDb : RoomDatabase() {

  abstract fun peopleDao(): CatsDao

}