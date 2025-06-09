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
import androidx.room.Transaction
import com.example.t_bank.data.local.entity.CategoryExpenseEntity
import com.example.t_bank.data.remote.datasource.BudgetRemoteDataSource
import com.example.t_bank.data.model.BudgetRequest
import com.example.t_bank.domain.usecase.model.Category
import com.example.t_bank.toApiModel
import com.example.t_bank.toEntity


class SettingsRepository @Inject constructor(
    private val monthlyBudgetDao: MonthlyBudgetDao,
    private val categoryDao: CategoryDao,
    private val categoryDistributionDao: CategoryDistributionDao,
    private val budgetRemoteDataSource: BudgetRemoteDataSource
) {

    @Transaction
    suspend fun saveMonthlyBudget(
        userId: Long,
        month: String,
        totalBudget: Float,
        categories: List<Category>
    ) {
        try {
            Log.d("SettingsRepository", "Saving monthly budget for month: $month, totalBudget: $totalBudget")
            val existingBudget = monthlyBudgetDao.getBudgetByMonth(month)

            var budgetId: Long

            if (existingBudget != null) {
                val updatedBudget = existingBudget.copy(totalBudget = totalBudget)
                monthlyBudgetDao.updateMonthlyBudget(updatedBudget)
                Log.d("SettingsRepository", "Updated existing budget with ID: ${existingBudget.id}")
                budgetId = existingBudget.id.toLong()
                categoryDistributionDao.deleteByBudgetId(budgetId.toInt())

            } else {
                val newBudget = MonthlyBudgetEntity(id = 0, month = month, totalBudget = totalBudget)
                budgetId = monthlyBudgetDao.insertMonthlyBudget(newBudget)
                Log.d("SettingsRepository", "Inserted new budget with ID: $budgetId")
            }
            val allCategoriesInDb = categoryDao.getAllCategories()
            val categoryMapInDb = allCategoriesInDb.associateBy { it.name }
            val categoryEntities = categories.map { it.toEntity() }

            categoryEntities.forEach { newCategory ->
                val existingCategory = categoryMapInDb[newCategory.name]
                if (existingCategory == null) {
                    categoryDao.insertCategories(newCategory)
                    Log.d("SettingsRepository", "Inserted new category: ${newCategory.name}")
                } else {
                    if (newCategory != existingCategory) {
                        categoryDao.updateCategory(newCategory.copy(id = existingCategory.id))
                        Log.d("SettingsRepository", "Updated category: ${newCategory.name}")
                    }
                }
            }

            val allCategories = categoryDao.getAllCategories()
            val categoryMap = allCategories.associateBy { it.name }
            val distributions = categoryEntities.mapNotNull { category ->
                categoryMap[category.name]?.let { savedCategory ->
                    CategoryDistributionEntity(
                        id = 0,
                        budgetId = budgetId.toInt(),
                        categoryId = savedCategory.id
                    )
                }
            }

            if (distributions.isNotEmpty()) {
                categoryDistributionDao.insertCategoryDistributions(distributions)
                Log.d("SettingsRepository", "Inserted ${distributions.size} category distributions.")
            } else {
                Log.e("SettingsRepository", "No valid distributions found")
                return
            }

            Log.d("SettingsRepository", "Saved or updated monthly budget and distributions.")

            logDatabaseContent()
            val apiBudget = BudgetRequest(
                userId = userId,
                income = totalBudget.toDouble(),
                categories = categories.map { it.toApiModel() }
            )
            //budgetRemoteDataSource.setBudget(apiBudget)
            Log.d("SettingsRepository", "Saved monthly budget to API.")

        } catch (e: Exception) {
            Log.e("SettingsRepository", "Error while saving monthly budget", e)
            throw RuntimeException("Failed to save or update budget", e)
        }
    }

    suspend fun logDatabaseContent() {
        Log.d("SettingsRepository", "Logging database content...")

        val categories = categoryDao.getAllCategories()
        Log.d("SettingsRepository", "Categories:")
        categories.forEach { category ->
            Log.d("SettingsRepository", "Category ID: ${category.id}, Name: ${category.name}, color ${category.colorResId}")
        }
        val monthlyBudgets = monthlyBudgetDao.getAllMonthlyBudgets()
        Log.d("SettingsRepository", "Monthly Budgets:")
        monthlyBudgets.forEach { budget ->
            Log.d("SettingsRepository", "Budget ID: ${budget.id}, Month: ${budget.month}, Total Budget: ${budget.totalBudget}")
        }

        val distributions = categoryDistributionDao.getAllCategoryDistributions()
        Log.d("SettingsRepository", "Category Distributions:")
        distributions.forEach { distribution ->
            Log.d("SettingsRepository", "Distribution ID: ${distribution.id}, Budget ID: ${distribution.budgetId}, Category ID: ${distribution.categoryId}")
        }

        val expenses = monthlyBudgetDao.getAllCategoryExpenses()
        Log.d("SettingsRepository", "Category Expenses:")
        expenses.forEach { expense ->
            Log.d("SettingsRepository", "Expense ID: ${expense.id}, Category ID: ${expense.categoryId}, Budget: ${expense.budget}, Amount Spent: ${expense.amountSpent}")
        }
    }


    fun getDefaultCategories(): List<CategoryEntity> {
        return listOf(
            CategoryEntity(1, "Продукты", R.drawable.ic_food, R.color.red, 25f),
            CategoryEntity(2, "Транспорт", R.drawable.ic_transport, R.color.blue, 15f),
            CategoryEntity(3, "Коммунальные услуги", R.drawable.ic_utilities, R.color.green, 10f),
            CategoryEntity(4, "Развлечения", R.drawable.ic_entertainment, R.color.yellow, 20f),
            CategoryEntity(5, "Накопления", R.drawable.ic_goals, R.color.dark_gray, 15f),
            CategoryEntity(6, "Другое", R.drawable.ic_other, R.color.purple, 15f)
        )
    }
}