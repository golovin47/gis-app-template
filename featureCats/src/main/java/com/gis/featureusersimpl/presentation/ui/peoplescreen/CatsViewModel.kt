package com.gis.featureusersimpl.presentation.ui.peoplescreen

import com.gis.repoimpl.domain.entitiy.Cat
import com.gis.repoimpl.domain.interactors.*
import com.gis.utils.BaseViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class CatsViewModel(
  private val observeCatsUseCase: ObserveCatsUseCase,
  private val loadNextCatsPageUseCase: LoadNextCatsPageUseCase,
  private val refreshCatsUseCase: RefreshCatsUseCase,
  private val searchCatsByNameUseCase: SearchCatsByNameUseCase,
  private val insertCatsUseCase: InsertCatsUseCase
) : BaseViewModel<CatsState, CatsStateChange>() {

  override fun initState(): CatsState = CatsState()

  override fun vmIntents(): Observable<CatsStateChange> =
    observeCatsUseCase.execute()
      .map { cats ->
        CatsStateChange.CatsReceived(
          cats.map { it.toPresentation() }
        )
      }
      .cast(CatsStateChange::class.java)
      .onErrorResumeNext { e: Throwable -> handleError(e) }
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())

  override fun viewIntents(intentStream: Observable<*>): Observable<CatsStateChange> =
    Observable.merge(listOf(

      intentStream.ofType(CatsIntent.LoadNextPage::class.java)
        .switchMap { event ->
          loadNextCatsPageUseCase.execute(event.page)
            .startWith(Observable.just(CatsStateChange.StartLoadNextPage))
            .cast(CatsStateChange::class.java)
            .onErrorResumeNext { e: Throwable ->
              Observable.just(CatsStateChange.LoadNextPageError(e), CatsStateChange.HideError)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
        },

      intentStream.ofType(CatsIntent.RefreshCats::class.java)
        .switchMap { event ->
          refreshCatsUseCase.execute()
            .andThen(Observable.just(CatsStateChange.RefreshFinished))
            .cast(CatsStateChange::class.java)
            .onErrorResumeNext { e: Throwable -> handleError(e) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
        },

      intentStream.ofType(CatsIntent.SearchById::class.java)
        .switchMap { event ->
          searchCatsByNameUseCase.execute(event.name)
            .map { CatsStateChange.CatsReceived(it.map { it.toPresentation() }) }
            .cast(CatsStateChange::class.java)
            .onErrorResumeNext { e: Throwable -> handleError(e) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
        },

      intentStream.ofType(CatsIntent.ItemMoved::class.java)
        .map { CatsStateChange.ItemMoved(it.from, it.to) },

      intentStream.ofType(CatsIntent.ItemDeleted::class.java)
        .map { CatsStateChange.ItemDeleted(it.position) }
    ))

  private fun handleError(e: Throwable) =
    Observable.just(
      CatsStateChange.Error(e),
      CatsStateChange.HideError
    )

  override fun reduceState(
    previousState: CatsState,
    stateChange: CatsStateChange
  ): CatsState =
    when (stateChange) {

      is CatsStateChange.StartLoading -> previousState.copy(loading = true)

      is CatsStateChange.CatsReceived -> previousState.copy(
        loading = false,
        cats = stateChange.cats
      )

      is CatsStateChange.StartLoadNextPage -> {
        val newCats = previousState.cats.toMutableList()
        newCats.add(CatsListItem.CatLoadingItem)
        previousState.copy(cats = newCats)
      }

      is CatsStateChange.LoadNextPageError -> {
        val newCats = previousState.cats.toMutableList()
        newCats.remove(CatsListItem.CatLoadingItem)
        previousState.copy(cats = newCats, error = stateChange.error)
      }

      is CatsStateChange.RefreshFinished -> previousState.copy(loading = false)

      is CatsStateChange.ItemMoved -> {
        val newList = previousState.cats.toMutableList()
        Collections.swap(newList, stateChange.from, stateChange.to)
        previousState.copy(cats = newList)
      }

      is CatsStateChange.ItemDeleted -> {
        val newList = previousState.cats.toMutableList()
        newList.removeAt(stateChange.position)
        previousState.copy(cats = newList)
      }

      is CatsStateChange.Error -> previousState.copy(loading = false, error = stateChange.error)

      is CatsStateChange.HideError -> previousState.copy(error = null)
    }

  private fun Cat.toPresentation(): CatsListItem =
    CatsListItem.CatDefaultItem(id = id, url = url)
}