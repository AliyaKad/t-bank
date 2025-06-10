package com.example.t_bank.domain.repository

import com.example.t_bank.domain.usecase.model.Category

interface CategoryRepository {
    fun getDefaultCategories(): List<Category>
}