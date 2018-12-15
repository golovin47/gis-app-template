package com.gis.jetpackplayground.application

import android.app.Application
import com.gis.featureloginscreen.di.loginModule
import com.gis.featurestartscreen.di.startScreenModule
import com.gis.featurecats.di.catsModule
import com.gis.jetpackplayground.di.*
import com.gis.navigation.di.navigationModule
import com.gis.repoimpl.di.repoModule
import com.gis.utils.di.utilsModule
import org.koin.android.ext.android.startKoin

class GisAppTemplate : Application() {

  override fun onCreate() {
    super.onCreate()

    startKoin(
      this,
      listOf(
        mainModule, loginModule, navigationModule,
        startScreenModule, repoModule, catsModule, utilsModule
      )
    )
  }
}