package com.gis.repoimpl.data.remote.datasource

import com.gis.repoimpl.domain.datasource.DataSource
import com.gis.repoapi.entity.Cat
import com.gis.repoimpl.data.remote.api.GisAppTemplateApi
import com.gis.repoimpl.data.remote.entity.CatR
import io.reactivex.Completable
import io.reactivex.Observable

class RemoteSource(private val api: GisAppTemplateApi) : DataSource {

  override fun getPeople(page: Int, limit: Int): Observable<List<Cat>> =
    api.getCats(page, limit)
      .map { list -> if (list.isNotEmpty()) list.map { it.toDomain() } else emptyList() }

  override fun findByName(name: String): Observable<List<Cat>> {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun insertPeople(people: List<Cat>): Completable =
    Completable.fromAction { val i = 0 }

  private fun CatR.toDomain() =
    Cat(
      id = id ?: "",
      url = url ?: ""
    )
}