package com.example.mvvmnew.repository

import androidx.paging.DataSource
import com.example.mvvmnew.database.UserDatabase
import com.example.mvvmnew.database.entity.UserEntity
import com.example.mvvmnew.api.Api
import com.example.mvvmnew.api.ApiResult
import com.example.mvvmnew.api.onSuccess
import com.example.mvvmnew.service.GithubApi
import com.example.mvvmnew.vo.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubRepository @Inject constructor(
    private val api: Api,
    private val githubApi: GithubApi,
    database: UserDatabase
) {
    private val dao = database.getDao()

    internal fun loadFromDatabase(sessionId: String): DataSource.Factory<Int, UserEntity> =
        dao.getDataSource(sessionId)

    internal suspend fun fetchUsers(
        sessionId: String,
        since: Int,
        pageSize: Int
    ): ApiResult<List<User>> =
        api.connect {
            githubApi.getUsers(since, pageSize)
        }.onSuccess {
            dao.insert(
                it.mapIndexed { index, user ->
                    UserEntity(
                        sessionId = sessionId,
                        index = since + index,
                        login = user.login,
                        avatarUrl = user.avatarUrl,
                        siteAdmin = user.siteAdmin,
                        id = user.id
                    )
                }
            )
        }

    internal suspend fun deleteBySessionId(sessionId: String) = dao.deleteBySession(sessionId)

    internal suspend fun deleteAll() = dao.deleteAll()
}