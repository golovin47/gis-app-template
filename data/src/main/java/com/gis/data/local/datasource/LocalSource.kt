package com.gis.data.local.datasource

import com.gis.data.local.db.JetPackDb
import com.gis.data.local.entity.PeopleL
import com.gis.domain.datasource.DataSource
import com.gis.domain.entity.People
import io.reactivex.Completable
import io.reactivex.Observable

class LocalSource(private val jetPackDb: JetPackDb) : DataSource {

  override fun getPeople(): Observable<List<People>> =
    jetPackDb.peopleDao().getAll()
      .map { if (it.isEmpty()) emptyList() else it.map { item -> item.toDomain() } }
      .toObservable()

  override fun findByName(name: String): Observable<List<People>> =
    jetPackDb.peopleDao().findByName(name)
      .map { if (it.isEmpty()) emptyList() else it.map { item -> item.toDomain() } }
      .toObservable()

  override fun insertPeople(people: List<People>): Completable = Completable.fromAction {
    jetPackDb.peopleDao().insertAll(people.map { it.toLocal() })
  }

  private fun PeopleL.toDomain() =
    People(
      id = id,
      name = name,
      metAt = metAt,
      contact = contact,
      email = email,
      facebook = facebook,
      twitter = twitter
    )

  private fun People.toLocal() =
    PeopleL(
      id = id,
      name = name,
      metAt = metAt,
      contact = contact,
      email = email,
      facebook = facebook,
      twitter = twitter
    )
}