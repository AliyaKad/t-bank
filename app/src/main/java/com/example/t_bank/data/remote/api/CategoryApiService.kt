package com.example.t_bank.data.remote.api

import com.example.t_bank.data.model.BudgetCategory
import com.example.t_bank.data.model.BudgetCategoryWithId
import com.example.t_bank.data.model.MessageResponse
import retrofit2.Response
import retrofit2.http.*

interface CategoryApiService {

    @GET("/api/v1/categories/{userId}")
    suspend fun getCategories(@Path("userId") userId: Int): Response<List<BudgetCategoryWithId>>

    @POST("/api/v1/categories/{userId}")
    suspend fun createCategory(
        @Path("userId") userId: Int,
        @Body category: BudgetCategory
    ): Response<MessageResponse>

    @PATCH("/api/v1/categories/{userId}/{categoryId}")
    suspend fun updateCategory(
        @Path("userId") userId: Int,
        @Path("categoryId") categoryId: Int,
        @Body category: BudgetCategory
    ): Response<MessageResponse>

    @DELETE("/api/v1/categories/{userId}/{categoryId}")
    suspend fun deleteCategory(
        @Path("userId") userId: Int,
        @Path("categoryId") categoryId: Int
    ): Response<MessageResponse>
}
