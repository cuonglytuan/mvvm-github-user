package com.example.mvvmnew.vo

import androidx.annotation.VisibleForTesting
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken

data class UserDetail(
    @SerializedName("login") val login: String,
    @SerializedName("avatar_url") val avatarUrl: String,
    @SerializedName("name") val name: String,
    @SerializedName("bio") val bio: String,
    @SerializedName("site_admin") val siteAdmin: Boolean,
    @SerializedName("location") val location: String,
    @SerializedName("blog") val blog: String
) {
    companion object {
        @VisibleForTesting(otherwise = VisibleForTesting.NONE)
        fun parseUserDetail(str: String): UserDetail {
            return Gson().fromJson(str, object : TypeToken<UserDetail>() {}.type)
        }
    }
}