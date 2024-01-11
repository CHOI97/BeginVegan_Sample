package com.example.beginvegan.src.data.model.restaurant

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class NearRestaurant(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("businessHours") val businessHours: String,
    @SerializedName("address") val address: Address,
    @SerializedName("latitude") val latitude: String,
    @SerializedName("longitude") val longitude: String,
    @SerializedName("imageUrl") val imageUrl: String,
    @SerializedName("menus") val menus: List<NearRestaurantMenus>
): Serializable
data class NearRestaurantMenus(
    @SerializedName("id") val id: Int,
    @SerializedName("imageUrl") val imageUrl: String?
)
