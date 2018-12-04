package com.gis.repoimpl.di

import com.gis.repoapi.interactors.*
import com.gis.repoimpl.data.local.datasource.LocalSource
import com.gis.repoimpl.data.local.db.GisAppTemplateDbProvider
import com.gis.repoimpl.data.remote.api.GisAppTemplateApiProvider
import com.gis.repoimpl.data.remote.datasource.RemoteSource
import com.gis.repoimpl.data.repository.CatsRepositoryImpl
import com.gis.repoimpl.domain.datasource.DataSource
import com.gis.repoimpl.domain.interactors.*
import com.gis.repoimpl.domain.repository.CatsRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

val repoModule = module {

  single { GisAppTemplateDbProvider.createDb(androidContext()).peopleDao() }

  single { GisAppTemplateApiProvider.createApi() }

  single<DataSource>("local") { LocalSource(get()) }

  single<DataSource>("remote") { RemoteSource(get()) }

  single<CatsRepository> {
    CatsRepositoryImpl(
      get("local"),
      get("remote")
    )
  }

  //Interactors
  factory<ObserveCatsUseCase> { ObserveCatsUseCaseImpl(get()) }

  factory<LoadNextCatsPageUseCase> { LoadNextCatsPageUseCaseImpl(get()) }

  factory<RefreshCatsUseCase> { RefreshCatsUseCaseImpl(get()) }

  factory<InsertCatsUseCase> { InsertCatsUseCaseImpl(get()) }

  factory<SearchCatsByNameUseCase> { SearchCatsByNameUseCaseImpl(get()) }
}