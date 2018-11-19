package com.gis.repoimpl.domain.interactors

import com.gis.repoapi.entity.People
import com.gis.repoapi.interactors.GetPeopleUseCase
import com.gis.repoapi.interactors.InsertPeopleUseCase
import com.gis.repoapi.interactors.RefreshPeopleUseCase
import com.gis.repoapi.interactors.SearchPeopleByNameUseCase
import com.gis.repoimpl.domain.repository.PeopleRepository
import io.reactivex.Observable

class InsertPeopleUseCaseImpl(private val repository: PeopleRepository) : InsertPeopleUseCase {

  override fun execute(request: List<People>) = repository.insertPeople(request)
}


class GetPeopleUseCaseImpl(private val repository: PeopleRepository) : GetPeopleUseCase {

  override fun execute(request: Any): Observable<List<People>> = repository.getPeople()
}


class RefreshPeopleUseCaseImpl(private val repository: PeopleRepository) : RefreshPeopleUseCase {

  override fun execute(request: Any) = repository.refreshPeople()
}


class SearchPeopleByNameUseCaseImpl(private val repository: PeopleRepository) :
  SearchPeopleByNameUseCase {

  override fun execute(request: String) = repository.findByName(request)
}