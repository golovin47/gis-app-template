package com.gis.repoapi.interactors

import com.gis.repoapi.entity.Cat
import io.reactivex.Completable
import io.reactivex.Observable

interface ObserveCatsUseCase {

  fun execute(): Observable<List<Cat>>
}


interface LoadNextCatsPageUseCase {

  fun execute(page:Int): Completable
}


interface SearchCatsByNameUseCase {

  fun execute(request: String): Observable<List<Cat>>
}


interface InsertCatsUseCase {

  fun execute(request: List<Cat>): Completable
}


interface RefreshCatsUseCase {

  fun execute(): Completable
}


