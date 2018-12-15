package com.gis.repoimpl.domain.datasource

import com.gis.repoimpl.domain.entitiy.User
import io.reactivex.Completable
import io.reactivex.Observable

interface UserDataSource {

  fun getUser(): Observable<User>

  fun saveUser(user: User): Completable

  fun removeUser(): Completable
}