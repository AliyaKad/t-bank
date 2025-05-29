package com.example.t_bank.domain.usecase

import android.util.Log
import com.example.t_bank.domain.usecase.model.Category
import com.example.t_bank.data.repository.CategoryForTransactionsRepository

import javax.inject.Inject

class GetCategoriesForTransactionsUseCase @Inject constructor(
    private val repository: CategoryForTransactionsRepository
) {
    suspend operator fun invoke(userId: Int): List<Category> {
        Log.d("GetCategoriesUseCase", "Fetching categories for userId: $userId")
        val categories = repository.getCategories(userId)?.map {
            Category(id = it.id, name = it.name)
        } ?: emptyList()
        Log.d("GetCategoriesUseCase", "Fetched categories: $categories")
        return categories
    }
}