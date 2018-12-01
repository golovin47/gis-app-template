package com.gis.featureusersimpl.di

import com.gis.featureusers.FeatureUsersStarter
import com.gis.featureusersimpl.presentation.ui.peoplescreen.FeatureCatsStarterImpl
import com.gis.featureusersimpl.presentation.ui.peoplescreen.CatsViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val usersModule = module {

  factory<FeatureUsersStarter> { FeatureCatsStarterImpl(get()) }

  viewModel { CatsViewModel(get(), get(), get(), get()) }
}