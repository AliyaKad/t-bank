package com.example.t_bank.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.t_bank.data.local.entity.CategoryDistributionEntity

@Dao
interface CategoryDistributionDao {

    @Query("DELETE FROM category_distributions WHERE budgetId = :budgetId")
    suspend fun deleteByBudgetId(budgetId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategoryDistributions(distributions: List<CategoryDistributionEntity>)

    @Query("SELECT * FROM category_distributions")
    suspend fun getAllCategoryDistributions(): List<CategoryDistributionEntity>
}