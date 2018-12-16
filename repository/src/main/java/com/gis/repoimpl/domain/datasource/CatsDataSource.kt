package com.gis.repoimpl.domain.datasource

import com.gis.repoimpl.domain.entitiy.Cat
import io.reactivex.Completable
import io.reactivex.Observable

interface CatsDataSource {

  fun observeCats(): Observable<List<Cat>>

  fun getNextCatsPage(page: Int, limit: Int): Observable<List<Cat>>

  fun findByName(name:String): Observable<List<Cat>>

  fun insertCats(cats: List<Cat>): Completable
}