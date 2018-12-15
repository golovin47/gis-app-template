package com.gis.featureloginscreen.di

import com.gis.featureloginscreen.domain.interactors.LoginUseCase
import com.gis.featureloginscreen.presentation.LoginViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val loginModule = module {

  viewModel { LoginViewModel(get(), get("fromLoginToMain")) }

  factory { LoginUseCase(get()) }
}