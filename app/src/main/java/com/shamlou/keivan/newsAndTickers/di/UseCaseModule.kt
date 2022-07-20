
package com.shamlou.keivan.newsAndTickers.di

import com.shamlou.keivan.domain.repository.HomeRepository
import com.shamlou.keivan.domain.useCase.UseCaseGetNews
import com.shamlou.keivan.domain.useCase.UseCaseGetTickers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetTickersUseCases(homeRepository: HomeRepository) = UseCaseGetTickers(homeRepository)
    @Provides
    fun provideGetNewsUseCases(homeRepository: HomeRepository) = UseCaseGetNews(homeRepository)
}