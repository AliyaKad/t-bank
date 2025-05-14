package com.example.t_bank.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.t_bank.presentation.model.Category

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val iconResId: Int,
    val colorResId: Int,
    val percentage: Float
) {
    fun toDomainModel(): Category {
        return Category(
            name = name,
            iconResId = iconResId,
            colorResId = colorResId,
            percentage = percentage
        )
    }
}