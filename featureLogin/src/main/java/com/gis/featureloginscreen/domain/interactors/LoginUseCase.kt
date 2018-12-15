package com.gis.featureloginscreen.domain.interactors

import com.gis.repoimpl.domain.entitiy.User
import com.gis.repoimpl.domain.interactors.SaveUserUseCase
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class LoginUseCase(private val saveUserUseCase: SaveUserUseCase) {

  fun execute(name: String) =
    Observable.timer(3000, TimeUnit.MILLISECONDS)
      .switchMapCompletable { saveUserUseCase.execute(User(id = 1, name = name)) }
}