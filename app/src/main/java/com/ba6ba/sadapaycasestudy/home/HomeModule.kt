package com.ba6ba.sadapaycasestudy.home

import com.ba6ba.network.RetrofitProvider
import com.ba6ba.sadapaycasestudy.home.data.DefaultHomeRepository
import com.ba6ba.sadapaycasestudy.home.data.HomeApiService
import com.ba6ba.sadapaycasestudy.home.data.HomeRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import retrofit2.create

@Module
@InstallIn(ViewModelComponent::class)
interface HomeModule {

    @Binds
    fun bindHomeRepository(default: DefaultHomeRepository): HomeRepository
}

@Module
@InstallIn(SingletonComponent::class)
class HomeApiModule {

    @Provides
    fun provideHomeApiService(retrofitProvider: RetrofitProvider): HomeApiService =
        retrofitProvider.get().create()
}