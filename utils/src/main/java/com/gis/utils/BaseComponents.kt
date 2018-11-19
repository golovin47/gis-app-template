package com.gis.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel<State, StateChange> : ViewModel() {

  private val states = MutableLiveData<State>()
  private var disposable: Disposable? = null

  protected abstract fun initState(): State

  fun handleIntents(intentStream: Observable<*>) {
    if (disposable == null)
      disposable = Observable.merge(vmIntents(), viewIntents(intentStream))
        .scan(initState()) { previousState, stateChanges ->
          reduceState(previousState, stateChanges)
        }
        .subscribe { state -> states.value = state }
  }

  protected open fun vmIntents(): Observable<StateChange> = Observable.never()

  protected abstract fun viewIntents(intentStream: Observable<*>): Observable<StateChange>

  protected abstract fun reduceState(previousState: State, stateChange: StateChange): State

  fun stateReceived(): LiveData<State> = states

  override fun onCleared() {
    disposable?.dispose()
  }
}