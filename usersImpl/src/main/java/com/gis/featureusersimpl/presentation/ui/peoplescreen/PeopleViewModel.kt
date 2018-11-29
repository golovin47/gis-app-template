package com.gis.featureusersimpl.presentation.ui.peoplescreen

import com.gis.repoapi.interactors.GetPeopleUseCase
import com.gis.repoapi.interactors.InsertPeopleUseCase
import com.gis.repoapi.interactors.RefreshPeopleUseCase
import com.gis.repoapi.interactors.SearchPeopleByNameUseCase
import com.gis.utils.BaseViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class PeopleViewModel(
  private val getPeopleUseCase: GetPeopleUseCase,
  private val refreshPeopleUseCase: RefreshPeopleUseCase,
  private val searchPeopleByNameUseCase: SearchPeopleByNameUseCase,
  private val insertPeopleUseCase: InsertPeopleUseCase
) : BaseViewModel<PeopleState, PeopleStateChange>() {

  override fun initState(): PeopleState = PeopleState()

  override fun vmIntents(): Observable<PeopleStateChange> =
    getPeopleUseCase.execute(true)
      .map { people ->
        PeopleStateChange.PeopleReceived(
          people
        )
      }
      .cast(PeopleStateChange::class.java)
      .onErrorResumeNext { e: Throwable -> handleError(e) }
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())

  override fun viewIntents(intentStream: Observable<*>): Observable<PeopleStateChange> =
    Observable.merge(listOf(

      intentStream.ofType(PeopleIntent.RefreshPeople::class.java)
        .switchMap { event ->
          refreshPeopleUseCase.execute(true)
            .andThen(Observable.just(PeopleStateChange.RefreshFinished))
            .cast(PeopleStateChange::class.java)
            .onErrorResumeNext { e: Throwable -> handleError(e) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
        },

      intentStream.ofType(PeopleIntent.SearchByName::class.java)
        .switchMap { event ->
          searchPeopleByNameUseCase.execute(event.name)
            .map { PeopleStateChange.PeopleReceived(it) }
            .cast(PeopleStateChange::class.java)
            .onErrorResumeNext { e: Throwable -> handleError(e) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
        },

      intentStream.ofType(PeopleIntent.ItemMoved::class.java)
        .map { PeopleStateChange.ItemMoved(it.from, it.to) },

      intentStream.ofType(PeopleIntent.ItemDeleted::class.java)
        .map { PeopleStateChange.ItemDeleted(it.position) }
    ))

  private fun handleError(e: Throwable) =
    Observable.just(
      PeopleStateChange.Error(e),
      PeopleStateChange.HideError
    )

  override fun reduceState(
    previousState: PeopleState,
    stateChange: PeopleStateChange
  ): PeopleState =
    when (stateChange) {

      is PeopleStateChange.StartLoading -> previousState.copy(loading = true)

      is PeopleStateChange.PeopleReceived -> previousState.copy(
        loading = false,
        people = stateChange.people
      )

      is PeopleStateChange.RefreshFinished -> previousState.copy(loading = false)

      is PeopleStateChange.ItemMoved -> {
        val newList = previousState.people.toMutableList()
        Collections.swap(newList, stateChange.from, stateChange.to)
        previousState.copy(people = newList)
      }

      is PeopleStateChange.ItemDeleted -> {
        val newList = previousState.people.toMutableList()
        newList.removeAt(stateChange.position)
        previousState.copy(people = newList)
      }

      is PeopleStateChange.Error -> previousState.copy(loading = false, error = stateChange.error)

      is PeopleStateChange.HideError -> previousState.copy(error = null)
    }

}