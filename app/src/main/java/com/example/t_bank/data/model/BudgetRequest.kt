package com.example.t_bank.data.model

import com.google.gson.annotations.SerializedName

data class BudgetRequest(
    @SerializedName("userId") val userId: Long,
    @SerializedName("income") val income: Int,
    @SerializedName("categories") val categories: List<BudgetCategory>
)
