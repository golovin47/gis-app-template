package com.gis.navigation.di

import com.gis.navigation.AppNavigator
import org.koin.dsl.module.module

val navigationModule = module {

  single(createOnStart = true) { AppNavigator() }

  factory(name = "fromStartToLogin") { { get<AppNavigator>().goToLoginFromStartScreen() } }
  factory(name = "fromStartToMain") { { get<AppNavigator>().goToMainFromStartScreen() } }
  factory(name = "fromLoginToMain") { { get<AppNavigator>().goToMainFromLogin() } }
}