package com.example.t_bank.mapper

import com.example.t_bank.presentation.model.Category
import javax.inject.Inject

class CategoryUiMapper @Inject constructor() {

    fun toUiModel(domain: Category): Category {
        return Category(
            name = domain.name,
            iconResId = domain.iconResId,
            colorResId = domain.colorResId,
            percentage = domain.percentage
        )
    }

    fun toDomainModel(ui: Category): Category {
        return Category(
            name = ui.name,
            iconResId = ui.iconResId,
            colorResId = ui.colorResId,
            percentage = ui.percentage
        )
    }
}