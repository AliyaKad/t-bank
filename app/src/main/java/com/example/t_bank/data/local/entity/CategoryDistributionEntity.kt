package com.example.t_bank.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "category_distributions",
    foreignKeys = [
        ForeignKey(
            entity = MonthlyBudgetEntity::class,
            parentColumns = ["id"],
            childColumns = ["budgetId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["budgetId"]),
        Index(value = ["categoryId"])
    ]
)
data class CategoryDistributionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val budgetId: Int,
    val categoryId: Int
)