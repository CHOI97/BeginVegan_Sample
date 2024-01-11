package com.example.beginvegan.src.ui.view.login

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.beginvegan.config.BaseActivity
import com.example.beginvegan.databinding.ActivityLoginBinding
import com.example.beginvegan.src.data.model.auth.KaKaoAuth
import com.example.beginvegan.src.data.repository.auth.AuthRepositoryImpl
import com.example.beginvegan.src.ui.viewModel.login.LoginViewModel
import com.example.beginvegan.src.ui.viewModel.login.LoginViewModelFactory
import com.example.beginvegan.util.Constants
import com.kakao.sdk.auth.model.OAuthToken

/*
* 카카오 SDK를 통해 유저 정보를 받아온다.
* 받아온 유저 정보를 통해 server에게 Request 요청
* server를 통해 받아온 AccessToken을 저장
* AccessToken을 DataStore에 저장
* 화면을 전환한다.
*/
class LoginActivity : BaseActivity<ActivityLoginBinding>({ ActivityLoginBinding.inflate(it) }) {
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var loginViewModelFactory: LoginViewModelFactory
    override fun init() {
        // Permission - location
        checkPermission()

        // Repository Impl
        val authRepository = AuthRepositoryImpl()
        // ViewModel Factory
        loginViewModelFactory = LoginViewModelFactory(authRepository)
        loginViewModel = ViewModelProvider(this,loginViewModelFactory)[LoginViewModel::class.java]

        // UI Interface
        binding.btnLoginKakao.setOnClickListener {
            loginViewModel.loginWithKaKao(this)
        }
        loginViewModel.kakaoAuthResult
    }

    private fun checkPermission() {
        if (checkLocationService()) {
            permissionCheck()
        } else {
            Toast.makeText(this, "GPS를 켜주세요", Toast.LENGTH_SHORT).show()
        }
    }

    private fun permissionCheck() {
        val preference = this.getPreferences(MODE_PRIVATE)
        val isFirstCheck = preference.getBoolean("isFirstPermissionCheck", true)
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // 권한이 없는 상태
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                // 권한 거절
                val builder = AlertDialog.Builder(this)
                builder.setMessage("현재 위치를 확인하시려면 위치 권한을 허용해주세요.")
                builder.setPositiveButton("확인") { dialog, which ->
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        Constants.ACCESS_FINE_LOCATION
                    )
                }
                builder.setNegativeButton("취소") { dialog, which ->

                }
                builder.show()
            } else {
                if (isFirstCheck) {
                    // 최초 권한 요청
                    preference.edit().putBoolean("isFirstPermissionCheck", false).apply()
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        Constants.ACCESS_FINE_LOCATION
                    )
                } else {
                    val builder = AlertDialog.Builder(this)
                    builder.setMessage("현재 위치를 확인하시려면 설정에서 위치 권한을 허용해주세요.")
                    builder.setPositiveButton("설정으로 이동") { dialog, which ->
                        val intent = Intent(
                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.parse("package:${this.packageName}")
                        )
                        startActivity(intent)
                    }
                    builder.setNegativeButton("취소") { dialog, which ->

                    }
                    builder.show()
                }
            }
        } else {
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.ACCESS_FINE_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "위치 권한이 승인되었습니다", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "위치 권한이 거절되었습니다", Toast.LENGTH_SHORT).show()
                permissionCheck()
            }
        }
    }

    private fun checkLocationService(): Boolean {
        val locationManager =
            this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

}