package com.example.mvvmnew.service

import com.example.mvvmnew.vo.UserDetail
import com.example.mvvmnew.vo.User
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Named

interface GithubService {
    @Headers(
        "Accept: application/json;charset=utf-t",
        "Accept-Language: en"
    )
    @GET("users?")
    suspend fun getUsers(
        @Query("since") since: Int,
        @Query("per_page") pageSize: Int
    ): List<User>

    @Headers(
        "Accept: application/json;charset=utf-t",
        "Accept-Language: en"
    )
    @GET("users/{login}")
    suspend fun getUserDetail(@Path("login") login: String): UserDetail
}

class GithubApi @Inject constructor(
    @Named("gitService") private val githubService: GithubService,
) : GithubService {
    override suspend fun getUsers(since: Int, pageSize: Int): List<User> =
        githubService.getUsers(since, pageSize)

    override suspend fun getUserDetail(login: String): UserDetail =
        githubService.getUserDetail(login)

}