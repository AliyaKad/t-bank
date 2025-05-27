package com.example.t_bank.data.repository

import com.example.t_bank.data.model.BudgetCategoryWithId
import com.example.t_bank.data.remote.datasource.CategoryDataSource
import javax.inject.Inject

class CategoryForTransactionsRepository @Inject constructor(
private val dataSource: CategoryDataSource
) {
    suspend fun getCategories(userId: Int): List<BudgetCategoryWithId>? {
        return dataSource.getCategories(userId)
    }
}