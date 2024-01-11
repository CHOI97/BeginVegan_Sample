package com.example.beginvegan.src.data.repository.auth

import com.example.beginvegan.src.data.model.auth.AuthLogin
import com.example.beginvegan.src.data.model.auth.AuthSignOutResponse
import com.example.beginvegan.src.data.model.auth.AuthSignResponse
import com.example.beginvegan.src.data.model.auth.AuthTokenResponse
import com.example.beginvegan.src.data.model.auth.KakaoAuth

interface AuthRepository {
    suspend fun signIn(authLogin: AuthLogin): AuthSignResponse
    suspend fun signUp(kakaoAuth: KakaoAuth): AuthSignResponse
    suspend fun signOut(accessToken: String): AuthSignOutResponse
    suspend fun refreshToken(refreshToken: String): AuthTokenResponse
}