package com.example.beginvegan.src.data.repository.auth

import android.content.Context
import com.example.beginvegan.src.data.model.auth.AuthLogin
import com.example.beginvegan.src.data.model.auth.AuthSignOutResponse
import com.example.beginvegan.src.data.model.auth.AuthSignResponse
import com.example.beginvegan.src.data.model.auth.AuthTokenResponse
import com.example.beginvegan.src.data.model.auth.KaKaoAuth
import com.kakao.sdk.auth.model.OAuthToken

interface AuthRepository {
    suspend fun loginWithKaKaoAccount(context: Context): Result<OAuthToken>
    suspend fun getKaKaoUserData(): KaKaoAuth?
    suspend fun signIn(authLogin: AuthLogin): AuthSignResponse
    suspend fun signUp(kakaoAuth: KaKaoAuth): AuthSignResponse
    suspend fun signOut(accessToken: String): AuthSignOutResponse
    suspend fun refreshToken(refreshToken: String): AuthTokenResponse
}