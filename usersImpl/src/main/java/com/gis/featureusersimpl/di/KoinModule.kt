package com.gis.featureusersimpl.di

import com.gis.featureusersimpl.presentation.ui.peoplescreen.PeopleViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module


val usersModule = module {

  viewModel {
    PeopleViewModel(
      get("getPeople"),
      get("refreshPeople"),
      get("searchPeople"),
      get("insertPeople")
    )
  }
}
