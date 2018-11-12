package com.gis.jetpackplayground.di

import androidx.room.Room
import com.gis.data.local.datasource.LocalSource
import com.gis.data.local.db.JetPackDb
import com.gis.data.remote.datasource.RemoteSource
import com.gis.data.repository.PeopleRepositoryImpl
import com.gis.data.usecase.GetPeopleUseCase
import com.gis.data.usecase.InsertPeopleUseCase
import com.gis.data.usecase.RefreshPeopleUseCase
import com.gis.data.usecase.SearchPeopleByNameUseCase
import com.gis.domain.datasource.DataSource
import com.gis.domain.repository.PeopleRepository
import com.gis.jetpackplayground.ui.peoplescreen.PeopleViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val koinModule = module {

  single<DataSource>("local") { LocalSource(get()) }
  single<DataSource>("remote") { RemoteSource() }
  single {
    Room.databaseBuilder(androidApplication(), JetPackDb::class.java, "JetPackDb")
      .allowMainThreadQueries()
      .build()
  }
  single<PeopleRepository> { PeopleRepositoryImpl(get("local"), get("remote")) }

  //UseCases
  factory("getPeople") { GetPeopleUseCase(get()) }
  factory("refreshPeople") { RefreshPeopleUseCase(get()) }
  factory("insertPeople") { InsertPeopleUseCase(get()) }
  factory("searchPeople") { SearchPeopleByNameUseCase(get()) }

  //ViewModels
  viewModel {
    PeopleViewModel(
      get("getPeople"),
      get("refreshPeople"),
      get("searchPeople"),
      get("insertPeople")
    )
  }
}