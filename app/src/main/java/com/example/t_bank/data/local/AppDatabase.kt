package com.example.t_bank.data.local

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.t_bank.data.local.dao.CategoryDao
import com.example.t_bank.data.local.dao.CategoryDistributionDao
import com.example.t_bank.data.local.dao.MonthlyBudgetDao
import com.example.t_bank.data.local.entity.CategoryDistributionEntity
import com.example.t_bank.data.local.entity.CategoryEntity
import com.example.t_bank.data.local.entity.MonthlyBudgetEntity
import com.example.t_bank.data.repository.SettingsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        CategoryEntity::class,
        CategoryDistributionEntity::class,
        MonthlyBudgetEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun categoryDao(): CategoryDao
    abstract fun categoryDistributionDao(): CategoryDistributionDao
    abstract fun monthlyBudgetDao(): MonthlyBudgetDao


    companion object {
        private const val TAG = "AppDatabase"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "t_bank_database"
                ).addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        Log.d(TAG, "Database created. Pre-populating with default data...")

                        CoroutineScope(Dispatchers.IO).launch {
                            try {
                                val database = INSTANCE ?: throw IllegalStateException("Database not initialized")
                                val repository = SettingsRepository(
                                    categoryDao = database.categoryDao(),
                                    monthlyBudgetDao = database.monthlyBudgetDao(),
                                    categoryDistributionDao = database.categoryDistributionDao()
                                )
                                repository.saveDefaultCategories(repository.getDefaultCategories())
                                Log.d(TAG, "Default categories have been inserted into the database.")
                            } catch (e: Exception) {
                                Log.e(TAG, "Error while pre-populating database", e)
                            }
                        }
                    }

                    override fun onOpen(db: SupportSQLiteDatabase) {
                        super.onOpen(db)
                        Log.d(TAG, "Database opened.")

                        CoroutineScope(Dispatchers.IO).launch {
                            try {
                                val database = INSTANCE ?: throw IllegalStateException("Database not initialized")
                                val repository = SettingsRepository(
                                    categoryDao = database.categoryDao(),
                                    monthlyBudgetDao = database.monthlyBudgetDao(),
                                    categoryDistributionDao = database.categoryDistributionDao()
                                )

                                val categories = repository.getCategories()
                                if (categories.isEmpty()) {
                                    Log.d(TAG, "No categories found. Inserting default categories...")
                                    repository.saveDefaultCategories(repository.getDefaultCategories())
                                } else {
                                    Log.d(TAG, "Categories already exist in the database.")
                                }
                            } catch (e: Exception) {
                                Log.e(TAG, "Error while checking or populating database", e)
                            }
                        }
                    }
                }).build()
                INSTANCE = instance
                instance
            }
        }
    }
}