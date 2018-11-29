package com.gis.jetpackplayground.di

import com.gis.featureusers.FeatureUsersStarter
import com.gis.featureusersimpl.presentation.ui.peoplescreen.FeatureUsersStarterImpl
import com.gis.featureusersimpl.presentation.ui.peoplescreen.PeopleViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val usersModule = module {

  factory<FeatureUsersStarter> { FeatureUsersStarterImpl(get()) }

  viewModel { PeopleViewModel(get(), get(), get(), get()) }
}