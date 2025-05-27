package com.example.t_bank.presentation.model

object CategoryRepository {
    var categories = listOf<CategoryForTransaction>()
    fun getCategoryNameById(id: Int?): String? {
        return categories.find { it.id == id }?.name
    }
}