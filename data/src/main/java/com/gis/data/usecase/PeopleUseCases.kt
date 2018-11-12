package com.gis.data.usecase

import com.gis.domain.entity.People
import com.gis.domain.repository.PeopleRepository
import com.gis.domain.usecase.UseCase
import io.reactivex.Completable
import io.reactivex.Observable

class InsertPeopleUseCase(private val repository: PeopleRepository) :
  UseCase<List<People>, Completable> {

  override fun execute(request: List<People>) = repository.insertPeople(request)
}


class GetPeopleUseCase(private val repository: PeopleRepository) :
  UseCase<Any, Observable<List<People>>> {

  override fun execute(request: Any): Observable<List<People>> = repository.getPeople()
}


class RefreshPeopleUseCase(private val repository: PeopleRepository) : UseCase<Any, Completable> {

  override fun execute(request: Any) = repository.refreshPeople()
}


class SearchPeopleByNameUseCase(private val repository: PeopleRepository) :
  UseCase<String, Observable<List<People>>> {

  override fun execute(request: String) = repository.findByName(request)
}