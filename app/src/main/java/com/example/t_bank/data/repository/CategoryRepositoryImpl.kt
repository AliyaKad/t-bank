package com.example.t_bank.data.repository

import com.example.t_bank.R
import com.example.t_bank.data.local.entity.CategoryEntity
import com.example.t_bank.mapper.CategoryMapper
import com.example.t_bank.domain.repository.CategoryRepository
import com.example.t_bank.domain.usecase.model.Category
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryMapper: CategoryMapper
) : CategoryRepository {

    override fun getDefaultCategories(): List<Category> {
        val defaultEntities = listOf(
            CategoryEntity(1, "Продукты", R.drawable.ic_food, R.color.red, 25f),
            CategoryEntity(2, "Транспорт", R.drawable.ic_transport, R.color.blue, 15f),
            CategoryEntity(3, "Коммунальные услуги", R.drawable.ic_utilities, R.color.green, 10f),
            CategoryEntity(4, "Развлечения", R.drawable.ic_entertainment, R.color.yellow, 20f),
            CategoryEntity(5, "Накопления", R.drawable.ic_goals, R.color.dark_gray, 15f),
            CategoryEntity(6, "Другое", R.drawable.ic_other, R.color.purple, 15f)
        )
        return defaultEntities.map { categoryMapper.toDomainModel(it) }
    }
}
