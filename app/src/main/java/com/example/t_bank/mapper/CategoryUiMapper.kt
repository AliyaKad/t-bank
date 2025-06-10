package com.example.t_bank.mapper

import javax.inject.Inject

class CategoryUiMapper @Inject constructor() {

    fun toUiModel(domain: com.example.t_bank.domain.usecase.model.Category): com.example.t_bank.presentation.model.Category {
        return com.example.t_bank.presentation.model.Category(
            name = domain.name,
            iconResId = domain.iconResId,
            colorResId = domain.colorResId,
            percentage = domain.percentage
        )
    }

    fun toDomainModel(ui: com.example.t_bank.presentation.model.Category): com.example.t_bank.domain.usecase.model.Category {
        return com.example.t_bank.domain.usecase.model.Category(
            name = ui.name,
            iconResId = ui.iconResId,
            colorResId = ui.colorResId,
            percentage = ui.percentage
        )
    }
}