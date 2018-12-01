package com.gis.repoimpl.di

import com.gis.repoapi.interactors.GetCatsUseCase
import com.gis.repoapi.interactors.InsertCatsUseCase
import com.gis.repoapi.interactors.RefreshCatsUseCase
import com.gis.repoapi.interactors.SearchCatsByNameUseCase
import com.gis.repoimpl.data.local.datasource.LocalSource
import com.gis.repoimpl.data.local.db.GisAppTemplateDbProvider
import com.gis.repoimpl.data.remote.api.GisAppTemplateApiProvider
import com.gis.repoimpl.data.remote.datasource.RemoteSource
import com.gis.repoimpl.data.repository.PeopleRepositoryImpl
import com.gis.repoimpl.domain.datasource.DataSource
import com.gis.repoimpl.domain.interactors.GetCatsUseCaseImpl
import com.gis.repoimpl.domain.interactors.InsertCatsUseCaseImpl
import com.gis.repoimpl.domain.interactors.RefreshCatsUseCaseImpl
import com.gis.repoimpl.domain.interactors.SearchCatsByNameUseCaseImpl
import com.gis.repoimpl.domain.repository.PeopleRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

val repoModule = module {

  single { GisAppTemplateDbProvider.createDb(androidContext()).peopleDao() }

  single { GisAppTemplateApiProvider.createApi() }

  single<DataSource>("local") { LocalSource(get()) }

  single<DataSource>("remote") { RemoteSource(get()) }

  single<PeopleRepository> {
    PeopleRepositoryImpl(
      get("local"),
      get("remote")
    )
  }

  //Interactors
  factory<GetCatsUseCase> { GetCatsUseCaseImpl(get()) }

  factory<RefreshCatsUseCase> { RefreshCatsUseCaseImpl(get()) }

  factory<InsertCatsUseCase> { InsertCatsUseCaseImpl(get()) }

  factory<SearchCatsByNameUseCase> { SearchCatsByNameUseCaseImpl(get()) }
}