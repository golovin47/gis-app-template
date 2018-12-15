package com.gis.repoimpl.di

import android.content.Context.MODE_PRIVATE
import com.gis.repoimpl.data.local.datasource.CatsLocalSource
import com.gis.repoimpl.data.local.datasource.UserLocalSource
import com.gis.repoimpl.data.local.db.GisAppTemplateDbProvider
import com.gis.repoimpl.data.remote.api.GisAppTemplateApiProvider
import com.gis.repoimpl.data.remote.datasource.CatsRemoteSource
import com.gis.repoimpl.data.remote.datasource.UserRemoteSource
import com.gis.repoimpl.data.repository.CatsRepositoryImpl
import com.gis.repoimpl.data.repository.UserRepositoryImpl
import com.gis.repoimpl.domain.datasource.CatsDataSource
import com.gis.repoimpl.domain.datasource.UserDataSource
import com.gis.repoimpl.domain.interactors.*
import com.gis.repoimpl.domain.repository.CatsRepository
import com.gis.repoimpl.domain.repository.UserRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

val repoModule = module {

  single { GisAppTemplateDbProvider.createDb(androidContext()).peopleDao() }

  single { GisAppTemplateApiProvider.createApi() }

  single { androidContext().getSharedPreferences("sharedPrefs", MODE_PRIVATE) }


  //User
  single<UserDataSource>("local") { UserLocalSource(get()) }

  single<UserDataSource>("remote") { UserRemoteSource() }

  single<UserRepository> { UserRepositoryImpl(get("local"), get("remote")) }

  factory { ObserveUserUseCase(get()) }

  factory { SaveUserUseCase(get()) }

  factory { RemoveUserUseCase(get()) }


  //Cats
  single<CatsDataSource>("local") { CatsLocalSource(get()) }

  single<CatsDataSource>("remote") { CatsRemoteSource(get()) }

  single<CatsRepository> { CatsRepositoryImpl(get("local"), get("remote")) }

  factory { ObserveCatsUseCase(get()) }

  factory { LoadNextCatsPageUseCase(get()) }

  factory { RefreshCatsUseCase(get()) }

  factory { InsertCatsUseCase(get()) }

  factory { SearchCatsByNameUseCase(get()) }
}