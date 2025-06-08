package com.example.t_bank

import android.content.SharedPreferences
import com.example.t_bank.domain.usecase.RefreshTokenUseCase
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import java.io.IOException
import dagger.Lazy

class TokenAuthenticator(
    private val refreshTokenUseCase: Lazy<RefreshTokenUseCase>,
    private val sharedPreferences: SharedPreferences
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.code == 401) {
            try {
                val refreshToken = sharedPreferences.getString("refresh_token", null)
                    ?: throw IOException("Refresh token не найден")

                val refreshed = runBlocking {
                    refreshTokenUseCase.get()(refreshToken)                }

                sharedPreferences.edit().apply {
                    putString("access_token", refreshed.accessToken)
                    apply()
                }

                return response.request.newBuilder()
                    .header("Authorization", "Bearer ${refreshed.accessToken}")
                    .build()

            } catch (e: Exception) {
                sharedPreferences.edit().clear().apply()
                return null
            }
        }

        return null
    }
}
