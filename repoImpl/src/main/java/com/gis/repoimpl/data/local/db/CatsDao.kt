package com.gis.repoimpl.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gis.repoimpl.data.local.entity.CatL
import io.reactivex.Flowable

@Dao
interface CatsDao {

  @Query("SELECT * FROM CatL ORDER BY id ASC")
  fun getAll(): Flowable<List<CatL>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insert(cat: CatL): Long

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertAll(catList: List<CatL>): List<Long>

  @Query("DELETE FROM CatL")
  fun deleteAll(): Int

  @Query("SELECT * FROM CatL WHERE id = :id")
  fun find(id: Int): Flowable<List<CatL>>

  @Query("SELECT * FROM CatL WHERE id LIKE '%' || :id || '%'")
  fun findById(id: String): Flowable<List<CatL>>
}