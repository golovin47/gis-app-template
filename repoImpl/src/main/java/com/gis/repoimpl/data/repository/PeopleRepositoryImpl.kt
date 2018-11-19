package com.gis.repoimpl.data.repository

import com.gis.repoimpl.domain.datasource.DataSource
import com.gis.repoapi.entity.People
import com.gis.repoimpl.domain.repository.PeopleRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class PeopleRepositoryImpl(
  private val localSource: DataSource,
  private val remoteSource: DataSource
) :
  PeopleRepository {

  override fun getPeople() =
    localSource.getPeople()
      .doOnNext { people ->
        if (people.isEmpty()) getPeopleFromRemote().subscribeOn(Schedulers.io()).subscribe()
      }

  private fun getPeopleFromRemote() =
    remoteSource.getPeople().switchMapCompletable { localSource.insertPeople(it) }

  override fun findByName(name: String): Observable<List<People>> = localSource.findByName(name)

  override fun refreshPeople(): Completable = getPeopleFromRemote()

  override fun insertPeople(people: List<People>): Completable =
    Completable.fromAction { localSource.insertPeople(people) }
      .doOnComplete { remoteSource.insertPeople(people) }
}