package com.example.t_bank.domain.usecase

import com.example.t_bank.data.local.entity.CategoryEntity
import com.example.t_bank.data.repository.SettingsRepository
import javax.inject.Inject

class UpdateCategoryUseCase @Inject constructor(
    private val repository: SettingsRepository
) {
    suspend operator fun invoke(category: CategoryEntity) {
        repository.updateCategory(category)
    }
}