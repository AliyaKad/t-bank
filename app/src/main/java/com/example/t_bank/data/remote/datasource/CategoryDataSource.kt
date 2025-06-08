package com.example.t_bank.data.remote.datasource

import com.example.t_bank.data.model.BudgetCategory
import com.example.t_bank.data.model.BudgetCategoryWithId

interface CategoryDataSource {
    suspend fun getCategories(userId: Int): List<BudgetCategoryWithId>?
    suspend fun createCategory(userId: Int, category: BudgetCategory)
    suspend fun updateCategory(userId: Int, categoryId: Int, category: BudgetCategory)
    suspend fun deleteCategory(userId: Int, categoryId: Int)
}
