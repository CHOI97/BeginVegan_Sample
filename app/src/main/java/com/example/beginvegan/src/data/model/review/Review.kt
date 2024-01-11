package com.example.beginvegan.src.data.model.review

import com.example.beginvegan.src.data.model.auth.Auth
import com.example.beginvegan.src.data.model.restaurant.Restaurant
import com.google.gson.annotations.SerializedName

data class Review(
    @SerializedName("reviews") val review: List<ReviewDetail>
)
data class ReviewDetail(
    @SerializedName("id") val id: Int,
    @SerializedName("content") val content: String,
    @SerializedName("date") val date: String,
    @SerializedName("restaurant") val restaurant: Restaurant,
    @SerializedName("user") val user: Auth
)
