package com.example.mvvmnew.usecase

import com.example.mvvmnew.repository.GithubRepository
import javax.inject.Inject

class GetUserDetailUseCase @Inject constructor(private val repository: GithubRepository) {
//    suspend fun getUserDetail(since: Long, pageSize: Int) = repository.get(since, pageSize)
}