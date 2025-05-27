package com.example.t_bank.domain.usecase

import com.example.t_bank.data.repository.SettingsRepository
import com.example.t_bank.mapper.CategoryDomainMapper
import com.example.t_bank.presentation.model.Category
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val repository: SettingsRepository,
    private val categoryMapper: CategoryDomainMapper
) {
    suspend operator fun invoke(): List<Category> {
        val entities = repository.getDefaultCategories()
        return entities.map { categoryMapper.toDomainModel(it) }
    }
}