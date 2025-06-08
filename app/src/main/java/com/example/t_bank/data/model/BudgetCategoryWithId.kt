package com.example.t_bank.data.model

import com.google.gson.annotations.SerializedName

data class BudgetCategoryWithId(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("percentage") val percentage: Double
)
