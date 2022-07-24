package com.ba6ba.sadapaycasestudy.home.data

import androidx.paging.PagingSource
import com.ba6ba.network.ApiResult
import com.ba6ba.network.ErrorResponse
import com.ba6ba.sadapaycasestudy.BaseTest
import com.ba6ba.sadapaycasestudy.managers.StringsResourceManager
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.*
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DefaultHomePagingSourceProviderTest : BaseTest() {

    @Mock
    lateinit var homeRepository: HomeRepository

    @Mock
    lateinit var stringsResourceManager: StringsResourceManager

    private lateinit var homePagingSourceProvider: HomePagingSourceProvider

    @Before
    fun setUp() {
        homePagingSourceProvider =
            DefaultHomePagingSourceProvider(homeRepository, stringsResourceManager)
    }

    @Test
    fun `verify error from paging source in case of no internet`() = runTest {
        val message = "no internet"
        val pagingSource = PagingSource.LoadParams.Refresh<Int>(
            key = 1,
            loadSize = 12,
            placeholdersEnabled = false
        )
        val apiResult = ApiResult.getNetworkFailure(message = message)
        whenever(homeRepository.getRepositories(anyInt())).thenReturn(apiResult)
        whenever(stringsResourceManager.getString(anyInt())).thenReturn(message)
        val expectedResult = PagingSource.LoadResult.Error<Int, RepositoryItem>(Throwable(message))

        val actualResult = homePagingSourceProvider.get().load(pagingSource)

        assertEquals(
            expectedResult.throwable.message,
            (actualResult as PagingSource.LoadResult.Error).throwable.message
        )
    }

    @Test
    fun `verify error from paging source in case error from server`() = runTest {
        val message = "limit reached"
        val pagingSource = PagingSource.LoadParams.Append(
            key = 100,
            loadSize = 12,
            placeholdersEnabled = false
        )
        val apiResult = ApiResult.Failure(ErrorResponse(message = "limit reached", statusCode = 50))
        whenever(homeRepository.getRepositories(anyInt())).thenReturn(apiResult)
        whenever(stringsResourceManager.getString(anyInt())).thenReturn(message)
        val expectedResult = PagingSource.LoadResult.Error<Int, RepositoryItem>(Throwable(message))

        val actualResult = homePagingSourceProvider.get().load(pagingSource)

        assertEquals(
            expectedResult.throwable.message,
            (actualResult as PagingSource.LoadResult.Error).throwable.message
        )
    }

    @Test
    fun `verify success from paging source`() = runTest {
        val pagingSource = PagingSource.LoadParams.Refresh(
            key = 10,
            loadSize = 12,
            placeholdersEnabled = false
        )
        val item = RepositoryItem(
            1,
            "Kotlin",
            "Kotlin-lang",
            "any url",
            "description of language",
            12,
            "kt",
            120,
            RepositoryOwner(
                imageUrl = "image url of author",
                userName = "author name",
                profileUrl = "url of profile of author"
            )
        )
        val list = listOf(item)
        val apiResult = ApiResult.Success(
            GitHubRepoResponse(
                totalCount = list.count(),
                incompleteResults = false,
                list
            )
        )
        whenever(homeRepository.getRepositories(anyInt())).thenReturn(apiResult)
        val expectedResult = PagingSource.LoadResult.Page(
            nextKey = null,
            prevKey = 9,
            data = list
        )

        val actualResult = homePagingSourceProvider.get().load(pagingSource)

        assertEquals(
            expectedResult.data,
            (actualResult as PagingSource.LoadResult.Page<Int, RepositoryItem>).data
        )
    }
}