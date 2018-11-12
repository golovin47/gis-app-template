package com.gis.domain.repository

import com.gis.domain.entity.People
import io.reactivex.Completable
import io.reactivex.Observable

interface PeopleRepository {

  fun getPeople(): Observable<List<People>>

  fun findByName(name: String): Observable<List<People>>

  fun insertPeople(people: List<People>): Completable

  fun refreshPeople(): Completable
}