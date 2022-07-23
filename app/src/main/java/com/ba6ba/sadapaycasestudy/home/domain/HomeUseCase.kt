package com.ba6ba.sadapaycasestudy.home.domain

import com.ba6ba.network.ApiResult
import com.ba6ba.sadapaycasestudy.FlowUseCase
import com.ba6ba.sadapaycasestudy.R
import com.ba6ba.sadapaycasestudy.home.data.HomeItemUiData
import com.ba6ba.sadapaycasestudy.home.data.HomeRepository
import com.ba6ba.sadapaycasestudy.home.data.RepositoryItem
import com.ba6ba.sadapaycasestudy.managers.StringsResourceManager
import com.ba6ba.sadapaycasestudy.managers.UiError
import com.ba6ba.sadapaycasestudy.managers.ViewState
import com.ba6ba.sadapaycasestudy.managers.default
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface HomeUseCase : FlowUseCase<Int, ViewState<List<HomeItemUiData>>> {
    override fun dispatcher(): CoroutineDispatcher = Dispatchers.IO
}

class DefaultHomeUseCase @Inject constructor(
    private val homeRepository: HomeRepository,
    private val stringsResourceManager: StringsResourceManager
) : HomeUseCase {
    override fun execute(parameters: Int): Flow<ViewState<List<HomeItemUiData>>> =
        flow {
            emit(ViewState.Loading)
            when (val response = homeRepository.getRepositories(parameters)) {
                is ApiResult.Success ->
                    emit(ViewState.Success(
                        response.data.items.orEmpty().map {
                            mapRepoToHomeUiData(it)
                        }
                    ))

                is ApiResult.Failure ->
                    emit(
                        ViewState.Error(
                            UiError(stringsResourceManager.getString(R.string.error_screen_description))
                        )
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
        )
    }
}