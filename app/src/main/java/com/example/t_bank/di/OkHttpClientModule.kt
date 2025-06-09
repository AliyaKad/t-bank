package com.example.t_bank.di

import android.content.SharedPreferences
import com.example.t_bank.TokenAuthenticator
import com.example.t_bank.domain.usecase.RefreshTokenUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import dagger.Lazy
import okhttp3.logging.HttpLoggingInterceptor


@Module
@InstallIn(SingletonComponent::class)
object OkHttpClientModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(
        sharedPreferences: SharedPreferences,
        authRepository: Lazy<RefreshTokenUseCase>
    ): OkHttpClient {

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val authInterceptor = Interceptor { chain ->
            val original = chain.request()
            val token = sharedPreferences.getString("access_token", null)

            if (token != null) {
                chain.proceed(original.newBuilder()
                    .header("Authorization", "Bearer $token")
                    .build())
            } else {
                chain.proceed(original)
            }
        }


        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .authenticator(TokenAuthenticator(authRepository, sharedPreferences))
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

}
