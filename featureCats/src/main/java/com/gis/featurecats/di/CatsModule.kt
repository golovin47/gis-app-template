package com.gis.featurecats.di

import com.gis.featurecats.presentation.ui.catsscreen.CatsViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val catsModule = module {

  viewModel { CatsViewModel(get(), get(), get(), get(), get()) }
}