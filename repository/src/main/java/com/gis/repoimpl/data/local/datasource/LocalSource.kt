package com.gis.repoimpl.data.local.datasource

import com.gis.repoimpl.data.local.db.CatsDao
import com.gis.repoimpl.data.local.entity.CatL
import com.gis.repoimpl.domain.datasource.DataSource
import com.gis.repoimpl.domain.entitiy.Cat
import io.reactivex.Completable
import io.reactivex.Observable

class LocalSource(private val catsDao: CatsDao) :
  DataSource {

  override fun observeCats(): Observable<List<Cat>> =
    catsDao.getAll()
      .map { if (it.isEmpty()) emptyList() else it.map { item -> item.toDomain() } }
      .toObservable()

  override fun getNextCatsPage(page: Int, limit: Int): Observable<List<Cat>> =
    throw UnsupportedOperationException("Local data source doesn't support getNextCatsPage()")

  override fun findByName(name: String): Observable<List<Cat>> =
    catsDao.findById(name)
      .map { if (it.isEmpty()) emptyList() else it.map { item -> item.toDomain() } }
      .toObservable()

  override fun insertCats(cats: List<Cat>): Completable = Completable.fromAction {
    catsDao.insertAll(cats.map { it.toLocal() })
  }

  private fun CatL.toDomain() =
    Cat(
      id = id,
      url = url
    )

  private fun Cat.toLocal() =
    CatL(
      id = id,
      url = url
    )
}