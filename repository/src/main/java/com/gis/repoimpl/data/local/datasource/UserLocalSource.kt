package com.gis.repoimpl.data.local.datasource

import android.content.SharedPreferences
import com.gis.repoimpl.data.local.entity.UserL
import com.gis.repoimpl.domain.datasource.UserDataSource
import com.gis.repoimpl.domain.entitiy.User
import io.reactivex.Completable
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class UserLocalSource(private val sharedPrefs: SharedPreferences) : UserDataSource {

  override fun getUser(): Observable<User> =
      Observable.fromCallable {
        val userFields =
          sharedPrefs.getString("user", User.empty().toLocal().toString())!!.split(",")
        return@fromCallable User(id = userFields[0].toInt(), name = userFields[1])
      }

  override fun saveUser(user: User): Completable = Completable.fromAction {
    sharedPrefs.edit().putString("user", user.toLocal().toString()).apply()
  }

  override fun removeUser(): Completable = Completable.fromAction {
    sharedPrefs.edit().remove("user")
  }

  private fun User.toLocal(): UserL {
    return UserL(id = id, name = name)
  }
}