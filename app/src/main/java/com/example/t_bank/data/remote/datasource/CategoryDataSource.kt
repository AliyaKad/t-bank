package com.example.t_bank.data.remote.datasource

import com.example.t_bank.data.model.BudgetCategory
import com.example.t_bank.data.model.BudgetCategoryWithId
import com.example.t_bank.data.remote.api.SmartBudgetApiService


class CategoryDataSource(private val apiService: SmartBudgetApiService) {

    suspend fun getCategories(userId: Int): List<BudgetCategoryWithId>? {
        val response = apiService.getCategories(userId)
        return if (response.isSuccessful) response.body() else null
    }

    suspend fun createCategory(userId: Int, category: BudgetCategory) {
        apiService.createCategory(userId, category)
    }

    suspend fun updateCategory(userId: Int, categoryId: Int, category: BudgetCategory) {
        apiService.updateCategory(userId, categoryId, category)
    }

    suspend fun deleteCategory(userId: Int, categoryId: Int) {
        apiService.deleteCategory(userId, categoryId)
    }
}
