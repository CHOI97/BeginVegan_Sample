package com.example.beginvegan.src.data.api

import com.example.beginvegan.src.data.model.auth.KaKaoAuth
import com.example.beginvegan.src.data.model.auth.AuthLogin
import com.example.beginvegan.src.data.model.auth.AuthSignResponse
import com.example.beginvegan.src.data.model.auth.AuthSignOutResponse
import com.example.beginvegan.src.data.model.auth.AuthTokenResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthRetrofitInterface {
    // 유저 회원가입
    @POST("/auth/sign-in")
    suspend fun postAuthSignIn(
        @Body auth:AuthLogin
    ): Call<AuthSignResponse>
    @POST("/auth/sign-up")
    suspend fun postAuthSignUp(
        @Body auth: KaKaoAuth
    ): Call<AuthSignResponse>

    // 유저 로그아웃
    @POST("/auth/sign-out")
    suspend fun postAuthSignOut(
        @Header("Authorization") accessToken: String?,
    ): Call<AuthSignOutResponse>

    // 토큰갱신
    @POST("/auth/refresh")
    suspend fun postTokenRefresh(
        @Body refreshToken :String
    ): Call<AuthTokenResponse>
}