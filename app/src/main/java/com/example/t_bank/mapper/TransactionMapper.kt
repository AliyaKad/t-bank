package com.example.t_bank.mapper

object TransactionMapper {
    fun mapFromData(data: com.example.t_bank.data.model.Transaction): com.example.t_bank.domain.usecase.model.Transaction {
        return com.example.t_bank.domain.usecase.model.Transaction(
            date = data.date,
            description = data.description,
            amount = data.amount.toInt(),
            category = if (data.category.isBlank()) null else data.category
        )
    }

    fun mapListFromData(list: List<com.example.t_bank.data.model.Transaction>?): List<com.example.t_bank.domain.usecase.model.Transaction> {
        return list!!.map { mapFromData(it) }
    }

    fun mapFromDto(dto: com.example.t_bank.data.model.Transaction): com.example.t_bank.domain.usecase.model.Transaction {
        return com.example.t_bank.domain.usecase.model.Transaction(
            date = dto.date,
            description = dto.description,
            amount = dto.amount.toInt(),
            category = if (dto.category.isBlank()) null else dto.category
        )
    }

    fun mapListFromDto(list: List<com.example.t_bank.data.model.Transaction>): List<com.example.t_bank.domain.usecase.model.Transaction> {
        return list.map { mapFromDto(it) }
    }

    fun mapToPresentation(transaction: com.example.t_bank.domain.usecase.model.Transaction): com.example.t_bank.presentation.model.Transaction {
        return com.example.t_bank.presentation.model.Transaction(
            date = transaction.date,
            description = transaction.description,
            amount = transaction.amount,
            category = transaction.category ?: "Без категории"
        )
    }

    fun mapListToPresentation(transactions: List<com.example.t_bank.domain.usecase.model.Transaction>): List<com.example.t_bank.presentation.model.Transaction> {
        return transactions.map { mapToPresentation(it) }
    }
}