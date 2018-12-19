package com.gis.repoimpl.data.repository

import com.gis.repoimpl.domain.datasource.CatsDataSource
import com.gis.repoimpl.domain.entitiy.Cat
import com.gis.repoimpl.domain.repository.CatsRepository
import io.reactivex.Completable
import io.reactivex.Observable

class CatsRepositoryImpl(
  private val localSourceCats: CatsDataSource,
  private val remoteSourceCats: CatsDataSource
) :
  CatsRepository {

  private val catsObservable: Observable<List<Cat>> = localSourceCats.observeCats().share()

  override fun observeCats(): Observable<List<Cat>> = catsObservable
    .doOnNext { cats ->
      if (cats.isEmpty()) getCatsFromRemote(1, 20).subscribe()
    }

  override fun loadNextCatsPage(page: Int): Completable = getCatsFromRemote(page, 20)

  private fun getCatsFromRemote(page: Int, limit: Int) =
    remoteSourceCats.getNextCatsPage(page, limit)
      .switchMapCompletable { localSourceCats.insertCats(it) }

  override fun findByName(name: String): Observable<List<Cat>> = localSourceCats.findByName(name)

  override fun refreshCats(): Completable = getCatsFromRemote(1, 20)

  override fun insertCats(people: List<Cat>): Completable =
    localSourceCats.insertCats(people)
      .doOnComplete { remoteSourceCats.insertCats(people) }
}