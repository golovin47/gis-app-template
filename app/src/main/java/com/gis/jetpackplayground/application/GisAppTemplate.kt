package com.gis.jetpackplayground.application

import android.app.Application
import com.gis.jetpackplayground.di.*
import org.koin.android.ext.android.startKoin

class GisAppTemplate : Application() {

  override fun onCreate() {
    super.onCreate()

    startKoin(
      this,
      listOf(loginModule, mainModule, navigationModule, startScreenModule, repoModule, usersModule)
    )
  }
}