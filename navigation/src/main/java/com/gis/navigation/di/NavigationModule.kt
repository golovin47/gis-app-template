package com.gis.navigation.di

import com.gis.navigation.AppCoordinator
import com.gis.navigation.AppNavigator
import org.koin.dsl.module.module

val navigationModule = module {

  single(createOnStart = true) { AppNavigator() }
  single(createOnStart = true) { AppCoordinator(get()) }

  factory(name = "fromStartToLogin") { { get<AppCoordinator>().goToLoginFromStartScreen() } }
  factory(name = "fromStartToCats") { { get<AppCoordinator>().goToMainFromStartScreen() } }
}