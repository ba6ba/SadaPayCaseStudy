package com.ba6ba.sadapaycasestudy.home.domain

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ba6ba.network.ApiResult
import com.ba6ba.sadapaycasestudy.R
import com.ba6ba.sadapaycasestudy.home.data.HomeRepository
import com.ba6ba.sadapaycasestudy.home.data.RepositoryItem
import com.ba6ba.sadapaycasestudy.managers.*
import javax.inject.Inject
import javax.inject.Provider

interface HomePagingSourceProvider : Provider<PagingSource<Int, RepositoryItem>>

class DefaultHomePagingSourceProvider @Inject constructor(
    private val homeRepository: HomeRepository,
    private val stringsResourceManager: StringsResourceManager
) : HomePagingSourceProvider {
    override fun get(): PagingSource<Int, RepositoryItem> {
        return object : PagingSource<Int, RepositoryItem>() {
            override fun getRefreshKey(state: PagingState<Int, RepositoryItem>): Int? = null

            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RepositoryItem> {
                val loadResult: LoadResult<Int, RepositoryItem>
                val page = params.key ?: Constants.DEFAULT_PAGE
                when (val response = homeRepository.getRepositories(page)) {
                    is ApiResult.Success ->
                        loadResult = LoadResult.Page(
                            data = response.data.items.orEmpty(),
                            prevKey = getPreviousKey(page),
                            nextKey = getNextKey(
                                page,
                                response.data.items?.count().default()
                            )
                        )

                    is ApiResult.Failure ->
                        loadResult = LoadResult.Error(
                            Throwable(
                                stringsResourceManager.getString(R.string.error_screen_description)
                            )
                        )
                }
                return loadResult
            }
        }
    }

    private fun getPreviousKey(page: Int): Int? =
        if (Constants.DEFAULT_PAGE == page) null else page

    private fun getNextKey(page: Int, currentFetchedItemsCount: Int): Int? =
        if (currentFetchedItemsCount < Constants.PAGE_LIMIT) null else page.inc()

    private fun <K : Any, V : Any> emptyLoadResult() =
        PagingSource.LoadResult.Page<K, V>(emptyList(), null, null, 0, 0)
}