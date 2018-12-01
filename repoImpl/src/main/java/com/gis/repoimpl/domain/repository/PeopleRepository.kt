package com.gis.repoimpl.domain.repository

import com.gis.repoapi.entity.Cat
import io.reactivex.Completable
import io.reactivex.Observable

interface PeopleRepository {

  fun getPeople(): Observable<List<Cat>>

  fun findByName(name: String): Observable<List<Cat>>

  fun insertPeople(people: List<Cat>): Completable

  fun refreshPeople(): Completable
}