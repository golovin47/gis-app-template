package com.gis.repoimpl.data.repository

import com.gis.repoapi.entity.Cat
import com.gis.repoimpl.domain.datasource.DataSource
import com.gis.repoimpl.domain.repository.CatsRepository
import io.reactivex.Completable
import io.reactivex.Observable

class CatsRepositoryImpl(
  private val localSource: DataSource,
  private val remoteSource: DataSource
) :
  CatsRepository {

  private val catsObservable: Observable<List<Cat>> = localSource.observeCats().share()

  override fun observeCats(): Observable<List<Cat>> = catsObservable
    .doOnNext { cats -> if (cats.isEmpty()) getPeopleFromRemote(1, 20).subscribe() }

  override fun loadNextCatsPage(page: Int): Completable = getPeopleFromRemote(page, 20)

  private fun getPeopleFromRemote(page: Int, limit: Int) =
    remoteSource.getNextCatsPage(page, limit).switchMapCompletable { localSource.insertCats(it) }

  override fun findByName(name: String): Observable<List<Cat>> = localSource.findByName(name)

  override fun refreshCats(): Completable = getPeopleFromRemote(1, 20)

  override fun insertCats(people: List<Cat>): Completable =
    Completable.fromAction { localSource.insertCats(people) }
      .doOnComplete { remoteSource.insertCats(people) }
}