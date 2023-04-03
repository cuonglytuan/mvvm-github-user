package com.example.mvvmnew.vo

import androidx.annotation.VisibleForTesting
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken

data class User(
    @SerializedName("login") val login: String,
    @SerializedName("avatar_url") val avatarUrl: String,
    @SerializedName("site_admin") val siteAdmin: Boolean,
    @SerializedName("id") val id: Long
) {
    companion object {
        @VisibleForTesting(otherwise = VisibleForTesting.NONE)
        fun parseUsers(str: String): List<User> {
            return Gson().fromJson(str, object : TypeToken<List<User>>() {}.type)
        }
    }
}