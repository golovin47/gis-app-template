package com.gis.repoimpl.domain.interactors

import com.gis.repoapi.entity.Cat
import com.gis.repoapi.interactors.GetCatsUseCase
import com.gis.repoapi.interactors.InsertCatsUseCase
import com.gis.repoapi.interactors.RefreshCatsUseCase
import com.gis.repoapi.interactors.SearchCatsByNameUseCase
import com.gis.repoimpl.domain.repository.PeopleRepository
import io.reactivex.Observable

class InsertCatsUseCaseImpl(private val repository: PeopleRepository) : InsertCatsUseCase {

  override fun execute(request: List<Cat>) = repository.insertPeople(request)
}


class GetCatsUseCaseImpl(private val repository: PeopleRepository) : GetCatsUseCase {

  override fun execute(request: Any): Observable<List<Cat>> = repository.getPeople()
}


class RefreshCatsUseCaseImpl(private val repository: PeopleRepository) : RefreshCatsUseCase {

  override fun execute(request: Any) = repository.refreshPeople()
}


class SearchCatsByNameUseCaseImpl(private val repository: PeopleRepository) :
  SearchCatsByNameUseCase {

  override fun execute(request: String) = repository.findByName(request)
}