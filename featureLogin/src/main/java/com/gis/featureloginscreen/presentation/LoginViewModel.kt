package com.gis.featureloginscreen.presentation

import com.gis.featureloginscreen.domain.interactors.LoginUseCase
import com.gis.utils.BaseViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoginViewModel(
  private val loginUseCase: LoginUseCase,
  private var goToMain: (() -> Unit)?
) :
  BaseViewModel<LoginState, LoginStateChange>() {

  override fun initState(): LoginState = LoginState()

  override fun viewIntents(intentStream: Observable<*>): Observable<LoginStateChange> =
    Observable.merge(
      listOf(
        intentStream.ofType(LoginIntent.NameChanged::class.java)
          .map { event -> LoginStateChange.NameChanged(event.name) },

        intentStream.ofType(LoginIntent.StartLogin::class.java)
          .switchMap { event ->
            loginUseCase.execute(event.name)
              .andThen(Observable.just(LoginStateChange.LoginSuccessful))
              .doOnNext { goToMain?.invoke() }
              .cast(LoginStateChange::class.java)
              .startWith(LoginStateChange.LoginStarted)
              .subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread())
          }
      )
    )

  override fun reduceState(previousState: LoginState, stateChange: LoginStateChange): LoginState =
    when (stateChange) {
      is LoginStateChange.NameChanged -> previousState.copy(name = stateChange.name)
      is LoginStateChange.LoginStarted -> previousState.copy(loading = true, error = null)
      is LoginStateChange.LoginSuccessful -> previousState.copy(loading = false, error = null)
      is LoginStateChange.LoginError -> previousState.copy(
        loading = false,
        error = stateChange.error
      )
    }

  override fun onCleared() {
    super.onCleared()
    goToMain = null
  }
}