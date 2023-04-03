package com.example.mvvmnew.usecase

import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.mvvmnew.database.entity.UserEntity
import com.example.mvvmnew.api.onError
import com.example.mvvmnew.api.onSuccess
import com.example.mvvmnew.repository.GithubRepository
import com.example.mvvmnew.vo.GithubState
import com.example.mvvmnew.vo.UserResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

private const val PAGE_COUNT = 30

class GetUserListUseCase @Inject constructor(private val repository: GithubRepository) {
    fun getUserList(scope: CoroutineScope): UserResult {

        val sessionId = UUID.randomUUID().toString()

        val dataSourceFactory = repository.loadFromDatabase(sessionId)

        val boundaryCallback = GitUserCallback(
            sessionId = sessionId,
            coroutineScope = scope,
            repository = repository
        )

        val data = LivePagedListBuilder(
            dataSourceFactory,
            PAGE_COUNT
        ).setBoundaryCallback(boundaryCallback).build()

        return UserResult(
            sessionId = sessionId, items = data, state = boundaryCallback.stateFlow
        )
    }
}

private class GitUserCallback(
    private val sessionId: String,
    private val coroutineScope: CoroutineScope,
    private val repository: GithubRepository,
) : PagedList.BoundaryCallback<UserEntity>() {

    private var start = 0

    private val _stateFlow = MutableSharedFlow<GithubState>()
    val stateFlow = _stateFlow.asSharedFlow()

    override fun onZeroItemsLoaded() {
        super.onZeroItemsLoaded()
        request()
    }

    override fun onItemAtEndLoaded(itemAtEnd: UserEntity) {
        super.onItemAtEndLoaded(itemAtEnd)
        request()
    }

    private fun request() {
        coroutineScope.launch {
            val isInitialLoading = start == 0
            if (isInitialLoading) {
                _stateFlow.emit(GithubState.Loading)
            }

            repository.fetchUsers(
                sessionId = sessionId,
                since = start,
                pageSize = PAGE_COUNT
            ).onSuccess { listUser ->
                start += listUser.size

                if (isInitialLoading) {
                    _stateFlow.emit(
                        if (listUser.isEmpty()) {
                            GithubState.Fetched.ZeroMatch
                        } else {
                            GithubState.Fetched.HasContents
                        }
                    )
                }
            }.onError {
                if (isInitialLoading) {
                    _stateFlow.emit(
                        GithubState.Failure.FetchError
                    )
                }
            }
        }
    }
}