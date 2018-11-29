package com.gis.jetpackplayground.di

import androidx.room.Room
import com.gis.repoapi.interactors.GetPeopleUseCase
import com.gis.repoapi.interactors.InsertPeopleUseCase
import com.gis.repoapi.interactors.RefreshPeopleUseCase
import com.gis.repoapi.interactors.SearchPeopleByNameUseCase
import com.gis.repoimpl.data.local.datasource.LocalSource
import com.gis.repoimpl.data.local.db.JetPackDb
import com.gis.repoimpl.data.remote.datasource.RemoteSource
import com.gis.repoimpl.data.repository.PeopleRepositoryImpl
import com.gis.repoimpl.domain.datasource.DataSource
import com.gis.repoimpl.domain.interactors.GetPeopleUseCaseImpl
import com.gis.repoimpl.domain.interactors.InsertPeopleUseCaseImpl
import com.gis.repoimpl.domain.interactors.RefreshPeopleUseCaseImpl
import com.gis.repoimpl.domain.interactors.SearchPeopleByNameUseCaseImpl
import com.gis.repoimpl.domain.repository.PeopleRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

val repoModule = module {

  single("DB") {
    Room.databaseBuilder(androidContext(), JetPackDb::class.java, "JetPackDb")
      .allowMainThreadQueries()
      .build()
  }

  single { get<JetPackDb>("DB").peopleDao() }

  single<DataSource>("local") { LocalSource(get()) }

  single<DataSource>("remote") { RemoteSource() }

  single<PeopleRepository> {
    PeopleRepositoryImpl(
      get("local"),
      get("remote")
    )
  }

  //Interactors
  factory<GetPeopleUseCase> { GetPeopleUseCaseImpl(get()) }

  factory<RefreshPeopleUseCase> { RefreshPeopleUseCaseImpl(get()) }

  factory<InsertPeopleUseCase> { InsertPeopleUseCaseImpl(get()) }

  factory<SearchPeopleByNameUseCase> { SearchPeopleByNameUseCaseImpl(get()) }
}