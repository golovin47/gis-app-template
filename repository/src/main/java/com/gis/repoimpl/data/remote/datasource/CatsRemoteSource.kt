package com.gis.repoimpl.data.remote.datasource

import com.gis.repoimpl.data.remote.api.GisAppTemplateApi
import com.gis.repoimpl.data.remote.entity.CatR
import com.gis.repoimpl.domain.datasource.CatsDataSource
import com.gis.repoimpl.domain.entitiy.Cat
import io.reactivex.Completable
import io.reactivex.Observable

class CatsRemoteSource(private val api: GisAppTemplateApi) : CatsDataSource {

  override fun observeCats(): Observable<List<Cat>> =
    throw UnsupportedOperationException("Remote data source doesn't support observeCats()")

  override fun getNextCatsPage(page: Int, limit: Int): Observable<List<Cat>> =
    api.getCats(page, limit)
      .map { list -> if (list.isNotEmpty()) list.map { it.toDomain() } else emptyList() }

  override fun findByName(name: String): Observable<List<Cat>> {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun insertCats(cats: List<Cat>): Completable =
    Completable.fromAction { val i = 0 }

  private fun CatR.toDomain() =
    Cat(
      id = id ?: "",
      url = url ?: ""
    )
}