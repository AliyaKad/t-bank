package com.example.t_bank.data.model

import com.google.gson.annotations.SerializedName

data class BudgetCategoryStatus(
    @SerializedName("name") val name: String,
    @SerializedName("spent") val spent: Double,
    @SerializedName("remaining") val remaining: Double,
    @SerializedName("limit") val limit: Double
)
