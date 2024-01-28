package com.example.beginvegan.src.ui.viewModel.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beginvegan.src.data.model.auth.AuthLogin
import com.example.beginvegan.src.data.model.auth.KaKaoAuth
import com.example.beginvegan.src.data.repository.auth.AuthRepository
import com.example.beginvegan.src.data.repository.auth.AuthRepositoryImpl
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
/*
  유저가 버튼을 누르면 로그인 로직이 실행됨 해당로
      카카오 로그인시 성공한 후 카카오 로그인 정보에 대한 LiveData
      로그인 한 후에 유저정보를 어떻게 관리할까
      1번 DataStore를 통한 저장
      -> x 유저정보 변경시 DataStore를 통한 변경이 이루어짐
      -> 앱내에서 유저정보를 가지고 있는 것은 좋은 선택지가 아니라고 생각함
      -> 네트워크에서 실시간 반영으로 해야함

      2번 저장하지 않고 매번 accessToken을 통해서 불러온다
      -> 메인홈, 프로필에서 매번 유저 정보를 조회하자
      -> 유저 정보 조회용으로 간단한 API구현 해달라하자
      -> LoginViewModel에서는 카카오 계정에 대한 LiveData
      -> AccessToken에 대한 LiveData를 가지고 있자

   */
class LoginViewModel(private val authRepository: AuthRepository): ViewModel() {


    private var kakaoAuthResult= MutableLiveData<KaKaoAuth>()
    // 카카오 SDK 토큰 갱신을 위한 MutableLiveData
    private var kakaoAuthToken = MutableLiveData<Result<OAuthToken>>()
    private var _kakaoAccessToken = MutableLiveData<String>()
    val kakaoAccessToken: LiveData<String> get() = _kakaoAccessToken

    private lateinit var authLogin: AuthLogin

    fun loginWithKaKao(context: Context) = viewModelScope.launch(Dispatchers.IO){
        try{
            kakaoAuthToken.postValue(authRepository.loginWithKaKaoAccount(context))
            getKaKaoUserData()
        }catch (e: Exception){
            Timber.e(e.toString())
        }
    }
    private fun getKaKaoUserData() = viewModelScope.launch (Dispatchers.IO){
        try{
            kakaoAuthResult.postValue(authRepository.getKaKaoUserData())
            signIn()
        }catch(e:Exception){
            Timber.d("getKaKaoUserData error: $e")
        }
    }
    private fun signIn() = viewModelScope.launch (Dispatchers.IO){
        authLogin = AuthLogin(kakaoAuthResult.value!!.providerId,kakaoAuthResult.value!!.email)
        val response = authRepository.signIn(authLogin)
        if(response.isSuccessful){
            response.body().let{
                _kakaoAccessToken.postValue(it?.information?.accessToken)
                Timber.d("SIGN IN SUCCESS")
            }
        }else{
            Timber.d("SIGN IN ERROR: ${response.code()}")
        }
    }
}