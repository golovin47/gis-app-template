package com.gis.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

abstract class BaseViewModel<State, StateChange> : ViewModel() {

  private val states = MutableLiveData<State>()
  private var intentsDisposable: Disposable? = null

  protected abstract fun initState(): State

  fun handleIntents(intentStream: Observable<*>) {
    if (intentsDisposable == null)
      intentsDisposable = Observable.merge(vmIntents(), viewIntents(intentStream))
        .scan(initState()) { previousState, stateChanges ->
          reduceState(previousState, stateChanges)
        }
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe { state -> states.value = state }
  }

  protected open fun vmIntents(): Observable<StateChange> = Observable.never()

  protected abstract fun viewIntents(intentStream: Observable<*>): Observable<StateChange>

  protected abstract fun reduceState(previousState: State, stateChange: StateChange): State

  fun stateReceived(): LiveData<State> = states

  private fun disposeIntents() {
    intentsDisposable?.dispose()
  }

  override fun onCleared() {
    disposeIntents()
  }

}