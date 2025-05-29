package com.example.t_bank.data.remote.datasource

import android.content.Context
import android.util.Log
import com.example.t_bank.data.model.BudgetCategory
import com.example.t_bank.data.model.BudgetCategoryWithId
import com.example.t_bank.data.remote.api.CategoryApiService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class CategoryDataSourceImpl(private val apiService: CategoryApiService,  @ApplicationContext private val context: Context): CategoryDataSource {

//    override suspend fun getCategories(userId: Int): List<BudgetCategoryWithId>? {
//        val response = apiService.getCategories(userId)
//        return if (response.isSuccessful) response.body() else null
//    }

    override suspend fun getCategories(userId: Int): List<BudgetCategoryWithId> {
        return try {
            val json = withContext(Dispatchers.IO) {
                context.assets.open("categories.json").bufferedReader().use { it.readText() }
            }
            Log.d("CategoryDataSource", "JSON data loaded: $json")

            val typeToken = object : TypeToken<List<BudgetCategoryWithId>>() {}.type
            val categories = Gson().fromJson<List<BudgetCategoryWithId>>(json, typeToken) ?: emptyList()
            Log.d("CategoryDataSource", "Parsed categories: $categories")
            categories
        } catch (e: Exception) {
            Log.e("CategoryDataSource", "Error loading categories", e)
            emptyList()
        }
    }

    override suspend fun createCategory(userId: Int, category: BudgetCategory) {
        apiService.createCategory(userId, category)
    }

    override suspend fun updateCategory(userId: Int, categoryId: Int, category: BudgetCategory) {
        apiService.updateCategory(userId, categoryId, category)
    }

    override suspend fun deleteCategory(userId: Int, categoryId: Int) {
        apiService.deleteCategory(userId, categoryId)
    }
}
