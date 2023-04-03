package com.example.mvvmnew.database.entity

import androidx.room.Entity

@Entity(tableName = "users", primaryKeys = ["sessionId", "index"])
data class UserEntity(
    val sessionId: String,
    val index: Int,
    val login: String,
    val avatarUrl: String,
    val siteAdmin: Boolean,
    val id: Long
)