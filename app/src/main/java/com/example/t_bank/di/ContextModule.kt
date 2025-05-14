package com.example.t_bank.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ContextModule {

    @Provides
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }
}