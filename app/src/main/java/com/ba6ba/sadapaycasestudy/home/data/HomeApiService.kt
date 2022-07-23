package com.ba6ba.sadapaycasestudy.home.data

import com.ba6ba.sadapaycasestudy.managers.Constants
import com.ba6ba.sadapaycasestudy.managers.QueryConstants
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeApiService {

    @GET("basit/search/repositories")
    suspend fun getRepositories(
        @Query("page") page: Int,
        @Query("q") query: String = QueryConstants.LANGUAGE,
        @Query("sort") sort: String = QueryConstants.STARS,
        @Query("per_page") perPage: Int = Constants.PAGE_LIMIT
    ): GitHubRepoResponse
}