package com.example.t_bank.data.repository

import com.example.t_bank.R
import com.example.t_bank.data.local.dao.CategoryDao
import com.example.t_bank.data.local.dao.CategoryDistributionDao
import com.example.t_bank.data.local.dao.MonthlyBudgetDao
import com.example.t_bank.data.local.entity.CategoryDistributionEntity
import com.example.t_bank.data.local.entity.CategoryEntity
import com.example.t_bank.data.local.entity.MonthlyBudgetEntity
import javax.inject.Inject
import android.util.Log

class SettingsRepository @Inject constructor(
    private val categoryDao: CategoryDao,
    private val monthlyBudgetDao: MonthlyBudgetDao,
    private val categoryDistributionDao: CategoryDistributionDao
) {

    suspend fun saveDefaultCategories(categories: List<CategoryEntity>) {
        Log.d("SettingsRepository", "Saving default categories to database...")
        categoryDao.insertCategories(*categories.toTypedArray())
        Log.d("SettingsRepository", "Inserted ${categories.size} default categories into the database.")
    }

    suspend fun getCategories(): List<CategoryEntity> {
        Log.d("SettingsRepository", "Fetching categories from database...")
        val categories = categoryDao.getAllCategories()
        Log.d("SettingsRepository", "Retrieved ${categories.size} categories from the database.")

        return categories
    }

    suspend fun saveMonthlyBudget(month: String, totalBudget: Float, categories: List<CategoryEntity>) {
        Log.d("SettingsRepository", "Saving monthly budget for month: $month, totalBudget: $totalBudget")
        monthlyBudgetDao.insertMonthlyBudget(MonthlyBudgetEntity(month, totalBudget))

        val distributions = categories.map { category ->
            CategoryDistributionEntity(
                id = 0,
                month = month,
                categoryId = category.id,
                amount = totalBudget * (category.percentage / 100)
            )
        }
        categoryDistributionDao.insertCategoryDistributions(distributions)
        Log.d("SettingsRepository", "Saved monthly budget and distributions.")
    }


    suspend fun updateCategory(category: CategoryEntity) {
        categoryDao.updateCategory(category)
    }

    suspend fun getMonthlyBudgetWithDistributions(month: String): Pair<MonthlyBudgetEntity?, List<CategoryDistributionEntity>> {
        val budget = monthlyBudgetDao.getMonthlyBudget(month)
        val distributions = categoryDistributionDao.getCategoryDistributionsForMonth(month)
        return Pair(budget, distributions)
    }
    suspend fun getMonthlyBudget(month: String): MonthlyBudgetEntity? {
        return monthlyBudgetDao.getMonthlyBudget(month)
    }

    fun getDefaultCategories(): List<CategoryEntity> {
        return listOf(
            CategoryEntity(1, "Продукты", R.drawable.ic_food, R.color.red, 25f),
            CategoryEntity(2, "Транспорт", R.drawable.ic_transport, R.color.blue, 15f),
            CategoryEntity(3, "Коммунальные услуги", R.drawable.ic_utilities, R.color.green, 10f),
            CategoryEntity(4, "Развлечения", R.drawable.ic_entertainment, R.color.yellow, 20f),
            CategoryEntity(5, "Другое", R.drawable.ic_other, R.color.purple, 30f)
        )
    }

}