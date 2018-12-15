package com.gis.repoimpl.data.repository

import com.gis.repoimpl.domain.datasource.UserDataSource
import com.gis.repoimpl.domain.entitiy.User
import com.gis.repoimpl.domain.repository.UserRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit

class UserRepositoryImpl(
  private val userLocalSource: UserDataSource,
  private val userRemoteSource: UserDataSource
) : UserRepository {

  private val userPublisher =
    BehaviorSubject.createDefault<User>(userLocalSource.getUser().blockingFirst())

  override fun observeUser(): Observable<User> = userPublisher

  override fun saveUser(user: User): Completable {
    userPublisher.onNext(user)
    return userLocalSource.saveUser(user)
  }

  override fun removeUser(): Completable {
    userPublisher.onNext(User.empty())
    return userLocalSource.removeUser()
  }
}