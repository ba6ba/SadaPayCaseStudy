package com.ba6ba.sadapaycasestudy.home.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.ba6ba.sadapaycasestudy.FlowUseCase
import com.ba6ba.sadapaycasestudy.home.data.HomeItemUiData
import com.ba6ba.sadapaycasestudy.home.data.RepositoryItem
import com.ba6ba.sadapaycasestudy.managers.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface HomeUseCase : FlowUseCase<Unit, PagingData<HomeItemUiData>> {
    override fun dispatcher(): CoroutineDispatcher = Dispatchers.IO
}

class DefaultHomeUseCase @Inject constructor(
    private val homePagingSourceProvider: HomePagingSourceProvider
) : HomeUseCase {
    override fun execute(parameters: Unit): Flow<PagingData<HomeItemUiData>> =
        Pager(
            config = PagingConfig(Constants.PAGE_LIMIT),
            pagingSourceFactory = {
                homePagingSourceProvider.get()
            }
        ).flow.map { pagingData ->
            pagingData.map { repositoryItem ->
                mapRepoToHomeUiData(
                    repositoryItem
                )
            }
        }

    private fun mapRepoToHomeUiData(repositoryItem: RepositoryItem): HomeItemUiData {
        return HomeItemUiData(
            title = repositoryItem.name.default,
            description = repositoryItem.description.default,
            languageName = repositoryItem.language.default,
            stars = repositoryItem.stars.default().toString(),
            watches = repositoryItem.watchers.default().toString(),
            authorImage = repositoryItem.owner?.imageUrl.default,
            authorName = repositoryItem.owner?.userName.default,
            metadata = HomeItemUiData.Metadata(
                repositoryItem.id.default(),
                repositoryItem.repoUrl.default
            )
        )
    }
}