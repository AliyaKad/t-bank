package com.example.t_bank.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "category_distributions",
    foreignKeys = [
        ForeignKey(
            entity = MonthlyBudgetEntity::class,
            parentColumns = ["month"],
            childColumns = ["month"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class CategoryDistributionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val month: String,
    val categoryId: Int,
    val amount: Float
)