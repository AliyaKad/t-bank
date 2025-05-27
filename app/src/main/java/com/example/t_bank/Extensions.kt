package com.example.t_bank

import com.example.t_bank.data.local.entity.CategoryEntity
import com.example.t_bank.data.model.BudgetCategory

fun com.example.t_bank.presentation.model.Category.toDomainModel(): com.example.t_bank.domain.usecase.model.Category {
    return com.example.t_bank.domain.usecase.model.Category(
        name = this.name,
        iconResId = this.iconResId,
        colorResId = this.colorResId,
        percentage = this.percentage
    )
}
fun com.example.t_bank.domain.usecase.model.Category.toPresentationModel(): com.example.t_bank.presentation.model.Category {
    return com.example.t_bank.presentation.model.Category(
        name = this.name,
        iconResId = this.iconResId,
        colorResId = this.colorResId,
        percentage = this.percentage
    )
}

fun com.example.t_bank.domain.usecase.model.Category.toEntity(id: Int = 0): CategoryEntity {
    return CategoryEntity(
        id = id,
        name = this.name,
        iconResId = this.iconResId,
        colorResId = this.colorResId,
        percentage = this.percentage
    )
}

fun com.example.t_bank.domain.usecase.model.Category.toApiModel(): BudgetCategory {
    return BudgetCategory(
        name = this.name,
        percentage = this.percentage.toDouble()
    )
}