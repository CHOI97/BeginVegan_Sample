package com.example.beginvegan.src.data.repository.auth

import com.example.beginvegan.src.data.api.AuthRetrofitInterface
import com.example.beginvegan.src.data.model.auth.AuthLogin
import com.example.beginvegan.src.data.model.auth.AuthSignOutResponse
import com.example.beginvegan.src.data.model.auth.AuthSignResponse
import com.example.beginvegan.src.data.model.auth.AuthTokenResponse
import com.example.beginvegan.src.data.model.auth.KakaoAuth
import retrofit2.awaitResponse

class AuthRepositoryImpl(private val authRetrofitInterface: AuthRetrofitInterface) : AuthRepository {

    override suspend fun signIn(authLogin: AuthLogin): AuthSignResponse {
        return authRetrofitInterface.postAuthSignIn(authLogin).awaitResponse().body()!!
    }

    override suspend fun signUp(kakaoAuth: KakaoAuth): AuthSignResponse {
        return authRetrofitInterface.postAuthSignUp(kakaoAuth).awaitResponse().body()!!
    }

    override suspend fun signOut(accessToken: String): AuthSignOutResponse {
        return authRetrofitInterface.postAuthSignOut(accessToken).awaitResponse().body()!!
    }

    override suspend fun refreshToken(refreshToken: String): AuthTokenResponse {
        return authRetrofitInterface.postTokenRefresh(refreshToken).awaitResponse().body()!!
    }

}