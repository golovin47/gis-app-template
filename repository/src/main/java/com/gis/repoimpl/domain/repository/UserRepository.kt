package com.gis.repoimpl.domain.repository

import com.gis.repoimpl.domain.entitiy.User
import io.reactivex.Completable
import io.reactivex.Observable

interface UserRepository {

  fun observeUser(): Observable<User>

  fun saveUser(user: User): Completable

  fun removeUser(): Completable
}