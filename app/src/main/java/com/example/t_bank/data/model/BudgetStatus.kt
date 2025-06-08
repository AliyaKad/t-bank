package com.example.t_bank.data.model

import com.google.gson.annotations.SerializedName

data class BudgetStatus(
    @SerializedName("income") val income: Double,
    @SerializedName("categories") val categories: List<BudgetCategoryStatus>
)
