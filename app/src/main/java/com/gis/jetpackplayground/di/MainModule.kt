package com.gis.jetpackplayground.di

import androidx.navigation.NavController
import org.koin.dsl.module.module

val mainModule = module {

  single { (navController: NavController) -> navController }
}