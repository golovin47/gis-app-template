package com.gis.jetpackplayground.application

import android.app.Application
import com.gis.featureusersimpl.di.usersModule
import com.gis.repoimpl.di.interactorsModule
import com.gis.repoimpl.di.repoModule
import org.koin.android.ext.android.startKoin
import org.koin.android.ext.koin.with
import org.koin.standalone.StandAloneContext.loadKoinModules

class JetPackApp : Application() {

  override fun onCreate() {
    super.onCreate()

    startKoin(this, listOf(repoModule, interactorsModule, usersModule))
  }
}