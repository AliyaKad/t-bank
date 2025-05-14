package com.example.t_bank.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.t_bank.data.local.entity.MonthlyBudgetEntity
import com.example.t_bank.presentation.model.Category

@Dao
interface MonthlyBudgetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMonthlyBudget(budget: MonthlyBudgetEntity): Long

    @Query("SELECT * FROM monthly_budgets WHERE month = :month")
    suspend fun getMonthlyBudget(month: String): MonthlyBudgetEntity?

    @Query("DELETE FROM monthly_budgets WHERE month = :month")
    suspend fun deleteMonthlyBudget(month: String)

    @Query("SELECT * FROM monthly_budgets")
    suspend fun getAllMonthlyBudgets(): List<MonthlyBudgetEntity>

    @Query("SELECT COUNT(*) FROM monthly_budgets")
    suspend fun isDatabaseEmpty(): Boolean

    @Query("""
        SELECT mb.totalBudget, c.name AS categoryName, c.iconResId AS categoryIconResId, 
               c.colorResId AS categoryColorResId, c.percentage AS categoryPercentage
        FROM monthly_budgets mb
        INNER JOIN category_distributions cd ON mb.id = cd.budgetId
        INNER JOIN categories c ON cd.categoryId = c.id
        WHERE mb.month = :month
    """)
    suspend fun getBudgetAndCategoriesByMonth(month: String): List<BudgetCategoryResult>
}

data class BudgetCategoryResult(
    val totalBudget: Float,
    val categoryName: String,
    val categoryIconResId: Int,
    val categoryColorResId: Int,
    val categoryPercentage: Float
)

data class BudgetWithCategories(
    val totalBudget: Float,
    val categories: List<Category>
)
