package com.gis.jetpackplayground.di

import com.gis.naviagationapi.Navigator
import com.gis.navigationimpl.NavigatorImpl
import org.koin.dsl.module.module

val navigationModule = module {

  single<Navigator> { NavigatorImpl(get(), get(), get()) }

  factory(name = "startStartScreenFeature") { { get<Navigator>().navigateToStartScreen() } }
  factory(name = "startLoginFeature") { { get<Navigator>().navigateToLoginScreen() } }
  factory(name = "startUsersFeature") { { get<Navigator>().navigateToUsersScreen() } }
}