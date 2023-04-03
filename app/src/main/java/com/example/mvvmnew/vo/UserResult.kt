package com.example.mvvmnew.vo

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.mvvmnew.database.entity.UserEntity
import kotlinx.coroutines.flow.Flow

data class UserResult(
    val sessionId: String,
    val items: LiveData<PagedList<UserEntity>>,
    val state: Flow<GithubState>
)