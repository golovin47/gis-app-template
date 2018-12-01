package com.gis.repoimpl.domain.datasource

import com.gis.repoapi.entity.Cat
import io.reactivex.Completable
import io.reactivex.Observable

interface DataSource {

  fun getPeople(page: Int, limit: Int): Observable<List<Cat>>

  fun findByName(name:String): Observable<List<Cat>>

  fun insertPeople(people: List<Cat>): Completable
}