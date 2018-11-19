package com.gis.repoimpl.di

import androidx.room.Room
import com.gis.repoimpl.domain.datasource.DataSource
import com.gis.repoimpl.domain.repository.PeopleRepository
import com.gis.repoimpl.data.local.datasource.LocalSource
import com.gis.repoimpl.data.remote.datasource.RemoteSource
import com.gis.repoimpl.data.local.db.JetPackDb
import com.gis.repoimpl.domain.interactors.GetPeopleUseCaseImpl
import com.gis.repoimpl.domain.interactors.InsertPeopleUseCaseImpl
import com.gis.repoimpl.domain.interactors.RefreshPeopleUseCaseImpl
import com.gis.repoimpl.domain.interactors.SearchPeopleByNameUseCaseImpl
import com.gis.repoimpl.data.repository.PeopleRepositoryImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.module

val repoModule = module {

  single {
    Room.databaseBuilder(androidApplication(), JetPackDb::class.java, "JetPackDb")
      .allowMainThreadQueries()
      .build()
  }

  single<DataSource>("local") { LocalSource(get()) }

  single<DataSource>("remote") { RemoteSource() }

  single<PeopleRepository> {
    PeopleRepositoryImpl(
      get("local"),
      get("remote")
    )
  }
}

val interactorsModule = module {

  factory { GetPeopleUseCaseImpl(get()) }

  factory { RefreshPeopleUseCaseImpl(get()) }

  factory { InsertPeopleUseCaseImpl(get()) }

  factory { SearchPeopleByNameUseCaseImpl(get()) }
}