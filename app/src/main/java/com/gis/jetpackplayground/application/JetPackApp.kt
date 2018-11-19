package com.gis.jetpackplayground.application

import android.app.Application
import com.gis.featureusersimpl.di.usersModule
import com.gis.repoimpl.di.interactorsModule
import com.gis.repoimpl.di.repoModule
import org.koin.standalone.StandAloneContext.loadKoinModules

class JetPackApp : Application() {

  override fun onCreate() {
    super.onCreate()

    loadKoinModules(listOf(repoModule, interactorsModule, usersModule))
  }
}