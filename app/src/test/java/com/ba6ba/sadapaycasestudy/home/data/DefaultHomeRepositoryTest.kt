package com.ba6ba.sadapaycasestudy.home.data

import com.ba6ba.network.ApiResult
import com.ba6ba.sadapaycasestudy.BaseTest
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.exceptions.base.MockitoException
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DefaultHomeRepositoryTest : BaseTest() {

    @Mock
    lateinit var homeApiService: HomeApiService

    private lateinit var homeRepository: HomeRepository

    @Before
    fun setUp() {
        homeRepository = DefaultHomeRepository(homeApiService)
    }

    @Test
    fun `verify success result from api call of get repositories`() = runTest {
        val pageNumber = 12
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
        val response = GitHubRepoResponse(
            totalCount = list.count(),
            incompleteResults = false,
            list
        )
        whenever(homeApiService.getRepositories(pageNumber)).thenReturn(response)

        assertEquals(ApiResult.Success(response), homeRepository.getRepositories(pageNumber))
    }

    @Test
    fun `verify failure result from api call of get repositories`() = runTest {
        val pageNumber = 12
        val failureResult = ApiResult.technicalFailure()
        val message = failureResult.error.message
        whenever(homeApiService.getRepositories(pageNumber)).thenThrow(MockitoException(message))

        assertEquals(failureResult, homeRepository.getRepositories(pageNumber))
    }
}