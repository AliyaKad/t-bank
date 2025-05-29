package com.example.t_bank.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.t_bank.data.local.dao.CategoryDao
import com.example.t_bank.data.local.dao.CategoryDistributionDao
import com.example.t_bank.data.local.dao.MonthlyBudgetDao
import com.example.t_bank.data.local.entity.CategoryDistributionEntity
import com.example.t_bank.data.local.entity.CategoryEntity
import com.example.t_bank.data.local.entity.CategoryExpenseEntity
import com.example.t_bank.data.local.entity.MonthlyBudgetEntity

@Database(
    entities = [
        CategoryEntity::class,
        CategoryDistributionEntity::class,
        MonthlyBudgetEntity::class,
        CategoryExpenseEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun categoryDao(): CategoryDao
    abstract fun categoryDistributionDao(): CategoryDistributionDao
    abstract fun monthlyBudgetDao(): MonthlyBudgetDao
}
