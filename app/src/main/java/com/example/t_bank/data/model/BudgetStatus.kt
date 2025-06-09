package com.example.t_bank.data.model

import com.google.gson.annotations.SerializedName

data class BudgetStatus(
    @SerializedName("income") val income: Double,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("updatedAt") val updatedAt: String?,
    @SerializedName("expiredAt") val expiredAt: String?,
    @SerializedName("userId") val userId: Long,
    @SerializedName("categories") val categories: List<BudgetCategoryStatus>
)
