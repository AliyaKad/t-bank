package com.example.t_bank.presentation.model

data class CategoryForTransaction(
    val id: Int,
    val name: String
)

object CategoryRepository {
    val categories = listOf(
        CategoryForTransaction(1, "Еда"),
        CategoryForTransaction(2, "Транспорт"),
        CategoryForTransaction(3, "ЖКХ"),
        CategoryForTransaction(4, "Развлечения"),
        CategoryForTransaction(5, "Другое")
    )

    fun getCategoryNameById(id: Int?): String? {
        return categories.find { it.id == id }?.name
    }
}