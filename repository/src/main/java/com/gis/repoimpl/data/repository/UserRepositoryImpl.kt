package com.gis.repoimpl.data.repository

import com.gis.repoimpl.domain.datasource.UserDataSource
import com.gis.repoimpl.domain.entitiy.User
import com.gis.repoimpl.domain.repository.UserRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class UserRepositoryImpl(
  private val userLocalSource: UserDataSource,
  private val userRemoteSource: UserDataSource
) : UserRepository {

  private val userPublisher = BehaviorSubject.create<User>()

  override fun observeUser(): Observable<User> =
    userLocalSource.getUser()

  override fun saveUser(user: User): Completable {
    userPublisher.onNext(user)
    return userLocalSource.saveUser(user)
  }

  override fun removeUser(): Completable {
    userPublisher.onNext(User.emptyUser)
    return userLocalSource.removeUser()
  }
}