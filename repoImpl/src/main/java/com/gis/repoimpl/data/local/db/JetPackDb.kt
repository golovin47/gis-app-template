package com.gis.repoimpl.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gis.repoimpl.data.local.entity.PeopleL

@Database(entities = [PeopleL::class], version = 1, exportSchema = false)
abstract class JetPackDb : RoomDatabase() {

  abstract fun peopleDao(): PeopleDao

}