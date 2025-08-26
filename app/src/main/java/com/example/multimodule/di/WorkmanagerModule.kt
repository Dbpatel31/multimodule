package com.example.multimodule.di

import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WorkmanagerModule {

    @Provides
    @Singleton
    fun provideWorkManagerConfig(factory: HiltWorkerFactory) : Configuration = Configuration.Builder().setWorkerFactory(factory).build()
}