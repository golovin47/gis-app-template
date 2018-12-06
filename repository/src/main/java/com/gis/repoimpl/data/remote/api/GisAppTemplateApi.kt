package com.gis.repoimpl.data.remote.api

import com.gis.repoimpl.data.remote.entity.CatR
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface GisAppTemplateApi {

  @GET("v1/images/search")
  fun getCats(
    @Query("page") page: Int,
    @Query("limit") limit: Int
  ): Observable<List<CatR>>
}