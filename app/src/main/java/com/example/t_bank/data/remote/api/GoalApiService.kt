package com.example.t_bank.data.remote.api

import com.example.t_bank.data.model.DataGoal
import com.example.t_bank.data.model.GoalRequest
import com.example.t_bank.data.model.MessageResponse
import retrofit2.Response
import retrofit2.http.*

interface GoalApiService {

    @POST("/api/v1/goals")
    suspend fun createGoal(@Body goalRequest: GoalRequest): Response<MessageResponse>

    @GET("/api/v1/goals/{userId}")
    suspend fun getGoals(@Path("userId") userId: Int): Response<List<DataGoal>>

    @PUT("/api/v1/goals/{userId}/{goalId}")
    suspend fun updateGoal(
        @Path("userId") userId: Int,
        @Path("goalId") goalId: Int,
        @Body goalRequest: GoalRequest
    ): Response<MessageResponse>

    @DELETE("/api/v1/goals/{userId}/{goalId}")
    suspend fun deleteGoal(
        @Path("userId") userId: Int,
        @Path("goalId") goalId: Int
    ): Response<MessageResponse>
}
