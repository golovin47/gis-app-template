package com.gis.jetpackplayground.di

import com.gis.featurestartscreenapi.FeatureStartScreenStarter
import com.gis.featurestartscreenimpl.FeatureStartScreenStarterImpl
import org.koin.dsl.module.module

val startScreenModule = module {

  factory<FeatureStartScreenStarter> { FeatureStartScreenStarterImpl(get()) }
}