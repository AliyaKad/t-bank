package com.example.t_bank.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.t_bank.data.local.entity.CategoryExpenseEntity
import com.example.t_bank.data.local.entity.MonthlyBudgetEntity
import com.example.t_bank.domain.usecase.model.BudgetForAllMonths
import java.time.temporal.TemporalAmount

@Dao
interface MonthlyBudgetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMonthlyBudget(budget: MonthlyBudgetEntity): Long

    @Query("SELECT * FROM monthly_budgets WHERE month = :month")
    suspend fun getMonthlyBudget(month: String): MonthlyBudgetEntity?

    @Query("SELECT * FROM monthly_budgets WHERE month = :month LIMIT 1")
    suspend fun getBudgetByMonth(month: String): MonthlyBudgetEntity?

    @Query("DELETE FROM category_distributions WHERE budgetId = :budgetId")
    suspend fun deleteDistributionsForBudget(budgetId: Int)

    @Update
    suspend fun updateMonthlyBudget(budget: MonthlyBudgetEntity)

    @Query("DELETE FROM monthly_budgets WHERE month = :month")
    suspend fun deleteMonthlyBudget(month: String)

    @Query("SELECT * FROM monthly_budgets")
    suspend fun getAllMonthlyBudgets(): List<MonthlyBudgetEntity>

    @Query("""
        SELECT mb.totalBudget, c.name AS categoryName, c.iconResId AS categoryIconResId, 
               c.colorResId AS categoryColorResId, c.percentage AS categoryPercentage
        FROM monthly_budgets mb
        INNER JOIN category_distributions cd ON mb.id = cd.budgetId
        INNER JOIN categories c ON cd.categoryId = c.id
        WHERE mb.month = :month
    """)
    suspend fun getBudgetAndCategoriesByMonth(month: String): List<BudgetCategoryResult>

    @Query("""
        SELECT 
            mb.totalBudget, 
            mb.month, 
            c.name AS categoryName,
            c.colorResId AS categoryColorResId,
            ce.budget AS budget,
            ce.amountSpent AS amountSpent
        FROM monthly_budgets mb
        INNER JOIN category_distributions cd ON mb.id = cd.budgetId
        INNER JOIN categories c ON cd.categoryId = c.id
        LEFT JOIN category_expenses ce ON c.id = ce.categoryId
        GROUP BY mb.month, c.id
    """)
    suspend fun getAllBudgets(): List<BudgetCategoryResultForMonth>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategoryExpenses(vararg expenses: CategoryExpenseEntity)

    @Query("SELECT * FROM category_expenses")
    suspend fun getAllCategoryExpenses(): List<CategoryExpenseEntity>
}

data class BudgetCategoryResult(
    val totalBudget: Float,
    val categoryName: String,
    val categoryIconResId: Int,
    val categoryColorResId: Int,
    val categoryPercentage: Float
)

data class BudgetCategoryResultForMonth(
    val totalBudget: Float,
    val month: String,
    val categoryName: String,
    val categoryColorResId: Int,
    val budget: Float,
    val amountSpent: Float
)

