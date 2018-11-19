package com.gis.repoimpl.domain.datasource

import com.gis.repoapi.entity.People
import io.reactivex.Completable
import io.reactivex.Observable

interface DataSource {

  fun getPeople(): Observable<List<People>>

  fun findByName(name:String): Observable<List<People>>

  fun insertPeople(people: List<People>): Completable
}