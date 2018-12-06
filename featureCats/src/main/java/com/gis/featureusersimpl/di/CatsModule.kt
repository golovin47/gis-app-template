package com.gis.featureusersimpl.di

import com.gis.featureusersimpl.presentation.ui.peoplescreen.CatsViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val catsModule = module {

  viewModel { CatsViewModel(get(), get(), get(), get(), get()) }
}