package com.shamlou.keivan.newsAndTickers.di

import android.content.Context
import com.google.gson.Gson
import com.shamlou.keivan.data.repository.HomeRepositoryImpl
import com.shamlou.keivan.data.repository.ReadFileFromAssets
import com.shamlou.keivan.domain.repository.HomeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {


    @Provides
    fun provideFileReader(@ApplicationContext appContext: Context): ReadFileFromAssets =
        ReadFileFromAssets(appContext)

    @Provides
    fun provideGson(): Gson = Gson()

    @Provides
    fun provideHomeRepository(fileReader: ReadFileFromAssets, gson: Gson): HomeRepository =
        HomeRepositoryImpl(fileReader, gson)
}