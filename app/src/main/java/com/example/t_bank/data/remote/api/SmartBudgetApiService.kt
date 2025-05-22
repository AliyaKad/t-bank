package com.example.t_bank.data.remote.api

import com.example.t_bank.data.model.BudgetRequest
import com.example.t_bank.data.model.BudgetStatus
import com.example.t_bank.data.model.BudgetCategoryWithId
import com.example.t_bank.data.model.BudgetCategory
import com.example.t_bank.data.model.Transaction
import com.example.t_bank.data.model.GoalRequest
import com.example.t_bank.data.model.Notification
import com.example.t_bank.data.model.DataGoal
import com.example.t_bank.data.model.MessageResponse
import com.example.t_bank.data.model.Rule
import com.example.t_bank.data.model.RuleRequest
import com.example.t_bank.presentation.model.Goal
import retrofit2.Response
import retrofit2.http.*

//interface SmartBudgetApiService {
//
//    // Budget endpoints
//    @POST("/api/budget")
//    suspend fun setBudget(@Body budgetRequest: BudgetRequest): Response<Unit>
//
//    @GET("/api/budget/status/{userId}")
//    suspend fun getBudgetStatus(@Path("userId") userId: Int): Response<BudgetStatus>
//
//    @PATCH("/api/budget/{userId}")
//    suspend fun updateIncome(
//        @Path("userId") userId: Int,
//        @Body incomeUpdate: Map<String, Double>
//    ): Response<Unit>
//
//    // Category endpoints
//    @GET("/api/categories/{userId}")
//    suspend fun getCategories(@Path("userId") userId: Int): Response<List<BudgetCategoryWithId>>
//
//    @POST("/api/categories/{userId}")
//    suspend fun createCategory(
//        @Path("userId") userId: Int,
//        @Body category: BudgetCategory
//    ): Response<Unit>
//
//    @PATCH("/api/categories/{userId}/{categoryId}")
//    suspend fun updateCategory(
//        @Path("userId") userId: Int,
//        @Path("categoryId") categoryId: Int,
//        @Body category: BudgetCategory
//    ): Response<Unit>
//
//    @DELETE("/api/categories/{userId}/{categoryId}")
//    suspend fun deleteCategory(
//        @Path("userId") userId: Int,
//        @Path("categoryId") categoryId: Int
//    ): Response<Unit>
//
//    // Transaction endpoints
//    @GET("/api/transactions/{userId}")
//    suspend fun getTransactions(@Path("userId") userId: Int): Response<List<Transaction>>
//
//    @PUT("/api/transactions/{userId}/{transactionId}")
//    suspend fun updateTransactionCategory(
//        @Path("userId") userId: Int,
//        @Path("transactionId") transactionId: Int,
//        @Body categoryUpdate: Map<String, String>
//    ): Response<Unit>
//
//    @POST("/api/transactions/import")
//    suspend fun importTransactions(@Body transactions: List<Transaction>): Response<Unit>
//
//    // Notification endpoints
//    @GET("/api/notifications/{userId}")
//    suspend fun getNotifications(@Path("userId") userId: Int): Response<List<Notification>>
//
//    // Goal endpoints
//    @POST("/api/goals")
//    suspend fun createGoal(@Body goalRequest: GoalRequest): Response<Unit>
//
//    @GET("/api/goals/{userId}")
//    suspend fun getGoals(@Path("userId") userId: Int): Response<List<DataGoal>>
//
//    @PUT("/api/goals/{userId}/{goalId}")
//    suspend fun updateGoal(
//        @Path("userId") userId: Int,
//        @Path("goalId") goalId: Int,
//        @Body goalRequest: GoalRequest
//    ): Response<Unit>
//
//    @DELETE("/api/goals/{userId}/{goalId}")
//    suspend fun deleteGoal(
//        @Path("userId") userId: Int,
//        @Path("goalId") goalId: Int
//    ): Response<Unit>
//}
interface SmartBudgetApiService {

    // Budget endpoints
    @POST("/api/v1/budget")
    suspend fun setBudget(@Body budgetRequest: BudgetRequest): Response<MessageResponse>

    @GET("/api/v1/budget/status/{userId}")
    suspend fun getBudgetStatus(@Path("userId") userId: Int): Response<BudgetStatus>

    @PATCH("/api/v1/budget/{userId}")
    suspend fun updateIncome(
        @Path("userId") userId: Int,
        @Body incomeUpdate: Map<String, Double>
    ): Response<MessageResponse>

    // Category endpoints
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

    // Transaction endpoints
    @GET("/api/v1/transactions/{userId}")
    suspend fun getTransactions(@Path("userId") userId: Int): Response<List<Transaction>>

    @PUT("/api/v1/transactions/{userId}/{transactionId}")
    suspend fun updateTransactionCategory(
        @Path("userId") userId: Int,
        @Path("transactionId") transactionId: Int,
        @Body categoryUpdate: Map<String, String>
    ): Response<MessageResponse>

    @POST("/api/v1/transactions")
    suspend fun importTransactions(@Body transactions: List<Transaction>): Response<MessageResponse>

    // Rules endpoints
    @POST("/api/v1/rules/{userId}")
    suspend fun createRule(
        @Path("userId") userId: Int,
        @Body rule: RuleRequest
    ): Response<MessageResponse>

    @GET("/api/v1/rules/{userId}")
    suspend fun getRules(@Path("userId") userId: Int): Response<List<Rule>>

    @PUT("/api/v1/rules/{userId}/{ruleId}")
    suspend fun updateRule(
        @Path("userId") userId: Int,
        @Path("ruleId") ruleId: Int,
        @Body rule: RuleRequest
    ): Response<MessageResponse>

    @DELETE("/api/v1/rules/{userId}/{ruleId}")
    suspend fun deleteRule(
        @Path("userId") userId: Int,
        @Path("ruleId") ruleId: Int
    ): Response<MessageResponse>

    // Notification endpoints
    @GET("/api/v1/notifications/{userId}")
    suspend fun getNotifications(@Path("userId") userId: Int): Response<List<Notification>>

    // Goal endpoints
    @POST("/api/v1/goals")
    suspend fun createGoal(@Body goalRequest: GoalRequest): Response<MessageResponse>

    @GET("/api/v1/goals/{userId}")
    suspend fun getGoals(@Path("userId") userId: Int): Response<List<Goal>>

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
