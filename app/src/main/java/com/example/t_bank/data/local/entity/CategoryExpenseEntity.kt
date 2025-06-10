package com.example.t_bank.data.local.entity

import androidx.room.*

@Entity(
    tableName = "category_expenses",
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["categoryId"])
    ]
)
data class CategoryExpenseEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val categoryId: Int,
    val budget: Float,
    val amountSpent: Float
)