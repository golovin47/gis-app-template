package com.gis.repoimpl.domain.repository

import com.gis.repoimpl.domain.entitiy.Cat
import io.reactivex.Completable
import io.reactivex.Observable

interface CatsRepository {

  fun observeCats(): Observable<List<Cat>>

  fun loadNextCatsPage(page: Int): Completable

  fun refreshCats(): Completable

  fun findByName(name: String): Observable<List<Cat>>

  fun insertCats(people: List<Cat>): Completable
}