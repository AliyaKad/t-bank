package com.example.t_bank.mapper

import com.example.t_bank.data.local.entity.CategoryEntity
import com.example.t_bank.presentation.model.Category

class CategoryMapper {

    fun toDomainModel(entity: CategoryEntity): Category {
        return Category(
            name = entity.name,
            iconResId = entity.iconResId,
            colorResId = entity.colorResId,
            percentage = entity.percentage
        )
    }

    fun toEntity(domain: Category, id: Int = 0): CategoryEntity {
        return CategoryEntity(
            id = id,
            name = domain.name,
            iconResId = domain.iconResId,
            colorResId = domain.colorResId,
            percentage = domain.percentage
        )
    }
}