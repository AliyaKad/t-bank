package com.example.t_bank.data.repository

import android.util.Log
import com.example.t_bank.R
import com.example.t_bank.data.local.dao.CategoryDao
import com.example.t_bank.data.remote.datasource.BudgetRemoteDataSource
import com.example.t_bank.domain.usecase.model.BudgetStatus
import com.example.t_bank.domain.usecase.model.BudgetCategoryStatus
import javax.inject.Inject

class BudgetRepositoryImpl @Inject constructor(
    private val remoteDataSource: BudgetRemoteDataSource,
    private val categoryDao: CategoryDao
) : BudgetRepository {

    override suspend fun getBudgetStatusWithColors(userId: Long): BudgetStatus {
        Log.d("BudgetRepository", "Запрашиваем данные у API для userId = $userId")
        val response = remoteDataSource.getBudgetStatus(userId)
            ?: throw Exception("Ошибка загрузки бюджета")

        Log.d("BudgetRepository", "Получено из API: ${response.categories.size} категорий")
        for (category in response.categories) {
            Log.d("API Data", "$category")
        }

        Log.d("BudgetRepository", "Запрашиваем последние категории из БД")
        val latestCategories = categoryDao.getLatestCategories()
        val latestCategoryMap = latestCategories.associateBy { it.name }

        Log.d("BudgetRepository", "Получено из БД: ${latestCategories.size} категорий")
        for (category in latestCategories) {
            Log.d("DB Data", "$category")
        }

        Log.d("BudgetRepository", "Начинаем маппинг данных")
        val mappedCategories = response.categories.map { apiCategory ->
            val dbCategory = latestCategoryMap[apiCategory.name]

            Log.d("Mapping", "API: ${apiCategory.name}, DB: ${dbCategory?.name}")

            BudgetCategoryStatus(
                name = apiCategory.name,
                spent = apiCategory.spent,
                remaining = apiCategory.remaining,
                limit = apiCategory.limit,
                colorResId = dbCategory?.colorResId ?: R.color.blue
            ).apply {
                Log.d("Mapped Data", "Категория: $name, Цвет: $colorResId")
            }
        }

        Log.d("BudgetRepository", "Маппинг завершён, всего: ${mappedCategories.size} категорий")
        return BudgetStatus(
            income = response.income,
            categories = mappedCategories
        )
    }
}