package com.example.t_bank.mapper

import com.example.t_bank.data.local.entity.CategoryEntity
import com.example.t_bank.presentation.model.Category
import com.example.t_bank.presentation.model.CategoryForTransaction

class CategoryDomainMapper {

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
object CategoryMapper {


    fun mapFromDomain(domain: com.example.t_bank.domain.usecase.model.Category): CategoryForTransaction {
        return CategoryForTransaction(id = domain.id, name = domain.name)
    }

    fun mapListFromDomain(list: List<com.example.t_bank.domain.usecase.model.Category>): List<CategoryForTransaction> {
        return list.map { mapFromDomain(it) }
    }

    fun mapFromDto(dto: com.example.t_bank.data.model.BudgetCategoryWithId): com.example.t_bank.domain.usecase.model.Category {
        return com.example.t_bank.domain.usecase.model.Category(
            id = dto.id,
            name = dto.name
        )
    }

    fun mapListFromDto(list: List<com.example.t_bank.data.model.BudgetCategoryWithId>): List<com.example.t_bank.domain.usecase.model.Category> {
        return list.map { mapFromDto(it) }
    }
}