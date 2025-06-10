package com.example.t_bank.data.model

import com.google.gson.annotations.SerializedName

data class BudgetCategory(
    @SerializedName("name") val name: String,
    @SerializedName("percentage") val percentage: Int
)
