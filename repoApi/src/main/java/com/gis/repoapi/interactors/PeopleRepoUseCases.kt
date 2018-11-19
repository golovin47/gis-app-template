package com.gis.repoapi.interactors

import com.gis.repoapi.entity.People
import io.reactivex.Completable
import io.reactivex.Observable

interface InsertPeopleUseCase {

  fun execute(request: List<People>): Completable
}


interface GetPeopleUseCase {

  fun execute(request: Any): Observable<List<People>>
}


interface RefreshPeopleUseCase {

  fun execute(request: Any): Completable
}


interface SearchPeopleByNameUseCase {

  fun execute(request: String): Observable<List<People>>
}