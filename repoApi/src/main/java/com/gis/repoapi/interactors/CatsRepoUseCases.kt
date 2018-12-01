package com.gis.repoapi.interactors

import com.gis.repoapi.entity.Cat
import io.reactivex.Completable
import io.reactivex.Observable

interface InsertCatsUseCase {

  fun execute(request: List<Cat>): Completable
}


interface GetCatsUseCase {

  fun execute(request: Any): Observable<List<Cat>>
}


interface RefreshCatsUseCase {

  fun execute(request: Any): Completable
}


interface SearchCatsByNameUseCase {

  fun execute(request: String): Observable<List<Cat>>
}