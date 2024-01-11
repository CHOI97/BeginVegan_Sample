package com.example.beginvegan.src.data.repository.auth

import android.content.Context
import android.util.Log
import com.example.beginvegan.config.ApplicationClass
import com.example.beginvegan.src.data.api.AuthRetrofitInterface
import com.example.beginvegan.src.data.model.auth.AuthLogin
import com.example.beginvegan.src.data.model.auth.AuthSignOutResponse
import com.example.beginvegan.src.data.model.auth.AuthSignResponse
import com.example.beginvegan.src.data.model.auth.AuthTokenResponse
import com.example.beginvegan.src.data.model.auth.KaKaoAuth
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import retrofit2.Response
import retrofit2.awaitResponse
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class AuthRepositoryImpl() : AuthRepository {
    private val authRetrofitInterface: AuthRetrofitInterface =
        ApplicationClass.sRetrofit.create(AuthRetrofitInterface::class.java)

    override suspend fun loginWithKaKaoAccount(context: Context): Result<OAuthToken> {
        return try {
            val token = suspendCoroutine<OAuthToken> { continuation ->
                UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
                    if (error != null) {
                        continuation.resumeWithException(error)
                    } else if (token != null) {
                        continuation.resume(token)
                    }
                }
            }
            Result.success(token)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getKaKaoUserData(): KaKaoAuth? {
        return suspendCoroutine { continuation ->
            UserApiClient.instance.me { user, error ->
                if (error != null) {
                    Log.e("KaKao User", "사용자 정보 요청 실패", error)
                    continuation.resume(null)
                } else if (user != null) {
                    Log.i(
                        "KaKao User", "사용자 정보 요청 성공" +
                                "\n회원번호: ${user.id}" +
                                "\n이메일: ${user.kakaoAccount?.email}" +
                                "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                                "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}"
                    )
                    val auth = KaKaoAuth(
                        user.id.toString()!!,
                        user.kakaoAccount?.profile?.nickname.orEmpty(),
                        user.kakaoAccount?.email.orEmpty(),
                        user.kakaoAccount?.profile?.thumbnailImageUrl.orEmpty()
                    )
                    continuation.resume(auth)
                }
            }
        }
    }

    override suspend fun signIn(authLogin: AuthLogin): Response<AuthSignResponse> {
        return authRetrofitInterface.postAuthSignIn(authLogin)
    }

    override suspend fun signUp(kakaoAuth: KaKaoAuth): Response<AuthSignResponse> {
        return authRetrofitInterface.postAuthSignUp(kakaoAuth)
    }

    override suspend fun signOut(accessToken: String): Response<AuthSignOutResponse> {
        return authRetrofitInterface.postAuthSignOut(accessToken)
    }

    override suspend fun refreshToken(refreshToken: String): Response<AuthTokenResponse> {
        return authRetrofitInterface.postTokenRefresh(refreshToken)
    }

}