package com.gis.repoimpl.data.remote.datasource

import com.gis.repoimpl.domain.datasource.UserDataSource
import com.gis.repoimpl.domain.entitiy.User
import io.reactivex.Completable
import io.reactivex.Observable

class UserRemoteSource : UserDataSource {

  override fun getUser(): Observable<User> {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun saveUser(user: User): Completable {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun removeUser(): Completable {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }
}