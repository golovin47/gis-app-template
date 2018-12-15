package com.gis.featurestartscreenimpl.presentation.ui.startscreen

import com.gis.featurestartscreenimpl.presentation.ui.startscreen.StartScreenStateChange.*
import com.gis.repoimpl.domain.entitiy.User
import com.gis.repoimpl.domain.interactors.ObserveUserUseCase
import com.gis.utils.BaseViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class StartScreenViewModel(
  private val observeUserUseCase: ObserveUserUseCase,
  private var goToLogin: (() -> Unit)?,
  private var goToMain: (() -> Unit)?
) :
  BaseViewModel<StartScreenState, StartScreenStateChange>() {

  override fun initState(): StartScreenState = StartScreenState()

  override fun vmIntents(): Observable<StartScreenStateChange> =
    observeUserUseCase.execute()
      .map { user -> if (user == User.emptyUser) GoToLogin else GoToMain }
      .doOnNext { if (it is GoToLogin) goToLogin?.invoke() else goToMain?.invoke() }
      .doOnError { goToLogin?.invoke() }
      .onErrorResumeNext { e: Throwable -> Observable.just(Error(e)) }
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())

  override fun viewIntents(intentStream: Observable<*>): Observable<StartScreenStateChange> =
    Observable.never()

  override fun reduceState(
    previousState: StartScreenState,
    stateChange: StartScreenStateChange
  ): StartScreenState = StartScreenState()

  override fun onCleared() {
    goToLogin = null
    goToMain = null
    super.onCleared()
  }
}