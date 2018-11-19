package com.gis.repoimpl.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gis.repoimpl.data.local.entity.PeopleL
import io.reactivex.Flowable

@Dao
interface PeopleDao {

  @Query("SELECT * FROM PeopleL ORDER BY id ASC")
  fun getAll(): Flowable<List<PeopleL>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insert(people: PeopleL): Long

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertAll(peopleList: List<PeopleL>): List<Long>

  @Query("DELETE FROM PeopleL")
  fun deleteAll(): Int

  @Query("SELECT * FROM PeopleL WHERE id = :id")
  fun find(id: Int): Flowable<List<PeopleL>>

  @Query("SELECT * FROM PeopleL WHERE name LIKE '%' || :name || '%'")
  fun findByName(name: String): Flowable<List<PeopleL>>
}