package com.gis.repoimpl.data.local.datasource

import com.gis.repoimpl.domain.datasource.DataSource
import com.gis.repoapi.entity.People
import com.gis.repoimpl.data.local.db.PeopleDao
import com.gis.repoimpl.data.local.entity.PeopleL
import io.reactivex.Completable
import io.reactivex.Observable

class LocalSource(private val peopleDao: PeopleDao) :
  DataSource {

  override fun getPeople(): Observable<List<People>> =
    peopleDao.getAll()
      .map { if (it.isEmpty()) emptyList() else it.map { item -> item.toDomain() } }
      .toObservable()

  override fun findByName(name: String): Observable<List<People>> =
    peopleDao.findByName(name)
      .map { if (it.isEmpty()) emptyList() else it.map { item -> item.toDomain() } }
      .toObservable()

  override fun insertPeople(people: List<People>): Completable = Completable.fromAction {
    peopleDao.insertAll(people.map { it.toLocal() })
  }

  private fun PeopleL.toDomain() =
    com.gis.repoapi.entity.People(
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