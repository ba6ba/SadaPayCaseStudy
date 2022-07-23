package com.ba6ba.sadapaycasestudy.home.data

import com.ba6ba.network.ApiResult
import com.ba6ba.network.BaseRepository
import javax.inject.Inject

interface HomeRepository : BaseRepository {

    suspend fun getRepositories(page: Int): ApiResult<GitHubRepoResponse>
}

class DefaultHomeRepository @Inject constructor(
    private val homeApiService: HomeApiService
) : HomeRepository {
    override suspend fun getRepositories(page: Int): ApiResult<GitHubRepoResponse> {
        return execute { homeApiService.getRepositories(page) }
    }
}