package com.example.mvvmnew.ui.main

import androidx.lifecycle.*
import androidx.paging.PagedList
import com.example.mvvmnew.database.entity.UserEntity
import com.example.mvvmnew.usecase.GetUserListUseCase
import com.example.mvvmnew.vo.GithubState
import com.example.mvvmnew.vo.UserResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val getUserListUseCase: GetUserListUseCase,
): ViewModel() {

    private val searchResult = MutableLiveData<UserResult>()

    private val sessionId =
        searchResult.map { it.sessionId }.asFlow()
            .shareIn(viewModelScope, SharingStarted.Eagerly, 1)

    val userItems: LiveData<PagedList<UserEntity>> =
        searchResult.switchMap { it.items }

    val state: StateFlow<GithubState> = searchResult.asFlow().flatMapLatest { it.state }
        .stateIn(viewModelScope, SharingStarted.Eagerly, GithubState.None)

    init {
        viewModelScope.launch {
            getUsers()
        }
    }

    fun getUsers() {
        searchResult.postValue(
            getUserListUseCase.getUserList(viewModelScope)
        )
    }
}