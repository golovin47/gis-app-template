package com.gis.jetpackplayground.di

import com.gis.featureloginapi.FeatureLoginStarter
import com.gis.featureloginscreen.FeatureLoginStarterImpl
import org.koin.dsl.module.module

val loginModule = module {

  factory<FeatureLoginStarter> { FeatureLoginStarterImpl(get()) }
}