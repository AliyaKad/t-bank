package com.example.t_bank.domain.usecase

import com.example.t_bank.domain.repository.CategoryRepository
import com.example.t_bank.domain.usecase.model.Category
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val repository: CategoryRepository
) {
    suspend operator fun invoke(): List<Category> {
        return repository.getDefaultCategories()
    }
}
