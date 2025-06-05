package com.example.t_bank.di

import android.content.Context
import com.example.t_bank.AndroidResourceProvider
import com.example.t_bank.ResourceProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object PresentationModule {

    @Provides
    fun provideResourceProvider(context: Context): ResourceProvider {
        return AndroidResourceProvider(context)
    }
}
