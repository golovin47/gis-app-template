package com.gis.featurestartscreenimpl.di

import com.gis.featurestartscreenimpl.presentation.ui.startscreen.StartScreenViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val startScreenModule = module {

  viewModel { StartScreenViewModel(get(), get("fromStartToLogin"), get("fromStartToCats")) }
}