package com.gis.jetpackplayground.application

import android.app.Application
import com.gis.featureloginscreen.di.loginModule
import com.gis.featurestartscreenimpl.di.startScreenModule
import com.gis.featureusersimpl.di.usersModule
import com.gis.jetpackplayground.di.*
import com.gis.navigationimpl.di.navigationModule
import com.gis.repoimpl.di.repoModule
import com.gis.utils.di.utilsModule
import org.koin.android.ext.android.startKoin

class GisAppTemplate : Application() {

  override fun onCreate() {
    super.onCreate()

    startKoin(
      this,
      listOf(
        loginModule, mainModule, navigationModule,
        startScreenModule, repoModule, usersModule, utilsModule
      )
    )
  }
}