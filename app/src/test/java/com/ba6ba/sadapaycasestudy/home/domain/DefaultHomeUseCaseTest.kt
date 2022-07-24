package com.ba6ba.sadapaycasestudy.home.domain

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ba6ba.sadapaycasestudy.BaseTest
import com.ba6ba.sadapaycasestudy.collectToList
import com.ba6ba.sadapaycasestudy.home.data.HomePagingSourceProvider
import com.ba6ba.sadapaycasestudy.home.data.RepositoryItem
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DefaultHomeUseCaseTest : BaseTest() {

    @Mock
    lateinit var homePagingSourceProvider: HomePagingSourceProvider

    private lateinit var homeUseCase: HomeUseCase

    @Before
    fun setUp() {
        homeUseCase = DefaultHomeUseCase(homePagingSourceProvider)
    }

    @Test
    fun `verify paging data flow`() = runTest {
        val pagingSource = object : PagingSource<Int, RepositoryItem>() {
            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RepositoryItem> {
                return LoadResult.Page(emptyList(), null, null, 0, 0)
            }

            override fun getRefreshKey(state: PagingState<Int, RepositoryItem>): Int? {
                return null
            }
        }
        whenever(homePagingSourceProvider.get()).thenReturn(pagingSource)

        val actualResult = homeUseCase.invoke(Unit).take(1).firstOrNull()

        assertNotNull(actualResult)
    }
}