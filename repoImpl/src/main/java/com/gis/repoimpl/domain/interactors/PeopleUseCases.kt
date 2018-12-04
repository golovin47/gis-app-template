package com.gis.repoimpl.domain.interactors

import com.gis.repoapi.entity.Cat
import com.gis.repoapi.interactors.*
import com.gis.repoimpl.domain.repository.CatsRepository
import io.reactivex.Observable

class ObserveCatsUseCaseImpl(private val repository: CatsRepository) : ObserveCatsUseCase {

  override fun execute(): Observable<List<Cat>> = repository.observeCats()
}


class LoadNextCatsPageUseCaseImpl(private val repository: CatsRepository) :
  LoadNextCatsPageUseCase {

  override fun execute(page: Int) = repository.loadNextCatsPage(page)
}


class RefreshCatsUseCaseImpl(private val repository: CatsRepository) : RefreshCatsUseCase {

  override fun execute() = repository.refreshCats()
}


class SearchCatsByNameUseCaseImpl(private val repository: CatsRepository) :
  SearchCatsByNameUseCase {

  override fun execute(request: String) = repository.findByName(request)
}


class InsertCatsUseCaseImpl(private val repository: CatsRepository) : InsertCatsUseCase {

  override fun execute(request: List<Cat>) = repository.insertCats(request)
}