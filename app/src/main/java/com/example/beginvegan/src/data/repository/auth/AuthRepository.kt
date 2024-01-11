package com.example.beginvegan.src.data.repository.auth

import android.content.Context
import com.example.beginvegan.src.data.model.auth.AuthLogin
import com.example.beginvegan.src.data.model.auth.AuthSignOutResponse
import com.example.beginvegan.src.data.model.auth.AuthSignResponse
import com.example.beginvegan.src.data.model.auth.AuthTokenResponse
import com.example.beginvegan.src.data.model.auth.KaKaoAuth
import com.kakao.sdk.auth.model.OAuthToken
import retrofit2.Response

interface AuthRepository {
    suspend fun loginWithKaKaoAccount(context: Context): Result<OAuthToken>
    suspend fun getKaKaoUserData(): KaKaoAuth?
    suspend fun signIn(authLogin: AuthLogin): Response<AuthSignResponse>
    suspend fun signUp(kakaoAuth: KaKaoAuth): Response<AuthSignResponse>
    suspend fun signOut(accessToken: String): Response<AuthSignOutResponse>
    suspend fun refreshToken(refreshToken: String): Response<AuthTokenResponse>
}