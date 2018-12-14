package com.gis.repoimpl.domain.interactors

import com.gis.repoimpl.domain.entitiy.Cat
import com.gis.repoimpl.domain.repository.CatsRepository
import io.reactivex.Observable

class ObserveCatsUseCase(private val repository: CatsRepository) {

  fun execute(): Observable<List<Cat>> = repository.observeCats()
}


class LoadNextCatsPageUseCase(private val repository: CatsRepository) {

  fun execute(page: Int) = repository.loadNextCatsPage(page)
}


class RefreshCatsUseCase(private val repository: CatsRepository) {

  fun execute() = repository.refreshCats()
}


class SearchCatsByNameUseCase(private val repository: CatsRepository) {

  fun execute(request: String) = repository.findByName(request)
}


class InsertCatsUseCase(private val repository: CatsRepository) {

  fun execute(request: List<Cat>) = repository.insertCats(request)
}