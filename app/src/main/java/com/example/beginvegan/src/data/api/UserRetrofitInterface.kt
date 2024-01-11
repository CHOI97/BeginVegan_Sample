package com.example.beginvegan.src.data.api

import com.example.beginvegan.src.data.model.restaurant.RestaurantDetailResponse
import com.example.beginvegan.src.data.model.user.NickName
import com.example.beginvegan.src.data.model.user.UserModifyNameResponse
import com.example.beginvegan.src.data.model.user.UserResponse
import com.example.beginvegan.src.data.model.user.UserScrapResponse
import com.example.beginvegan.src.data.model.user.UserVeganResponse
import com.example.beginvegan.src.data.model.user.VeganType
import com.example.beginvegan.util.VeganTypes
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface UserRetrofitInterface {

    // AccessToken을 이용한 정보 조회
    @GET("/api/v1/users")
    fun getUser(
        @Header("Authorization") accessToken: String?,
    ): Call<UserResponse>


    // 유저 비건 타입 변경
    @POST("/api/v1/users/vegan-type")
    fun postUserVeganType(
        @Header("Authorization") accessToken: String?,
        @Body veganType: VeganType
    ): Call<UserVeganResponse>

    // 유저의 스크랩 조회
    @GET("/api/v1/bookmarks")
    fun getUserBookmarks(
        @Header("Authorization") accessToken: String?,
        @Query("page") page: Int
    ): Call<UserScrapResponse>

    // 유저 닉네임 변경
    @POST("/api/v1/users/nickname")
    fun postUserModifyName(
        @Header("Authorization") accessToken: String?,
        @Body nickname: NickName
    ):Call<UserModifyNameResponse>
}