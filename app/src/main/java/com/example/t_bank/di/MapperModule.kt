package com.example.t_bank.di

import com.example.t_bank.mapper.CategoryDomainMapper
import com.example.t_bank.mapper.CategoryMapper
import com.example.t_bank.mapper.CategoryUiMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MapperModule {

    @Provides
    @Singleton
    fun provideCategoryMapper(): CategoryDomainMapper {
        return CategoryDomainMapper()
    }

    @Provides
    @Singleton
    fun provideCategoryUiMapper(): CategoryUiMapper {
        return CategoryUiMapper()
    }
}