package com.gis.jetpackplayground.application

import android.app.Application
import com.gis.featureusersimpl.di.usersModule
import com.gis.repoimpl.di.interactorsModule
import com.gis.repoimpl.di.repoModule
import org.koin.android.ext.android.startKoin

class GisAppTemplate : Application() {

  override fun onCreate() {
    super.onCreate()

    startKoin(this, listOf(repoModule, interactorsModule, usersModule))
  }
}