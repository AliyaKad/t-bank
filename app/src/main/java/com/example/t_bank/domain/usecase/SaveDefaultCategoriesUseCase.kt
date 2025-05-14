package com.example.t_bank.domain.usecase

import com.example.t_bank.data.local.entity.CategoryEntity
import com.example.t_bank.data.repository.SettingsRepository
import javax.inject.Inject

class SaveDefaultCategoriesUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(categories: List<CategoryEntity>) {
        repository.saveDefaultCategories(categories)
    }
}