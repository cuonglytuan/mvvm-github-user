package com.example.mvvmnew.vo

sealed class GithubState {
    object None : GithubState()
    object Loading : GithubState()
    sealed class Fetched() : GithubState() {
        object ZeroMatch : Fetched()
        object HasContents : Fetched()
    }
    sealed class Failure : GithubState(){
        object FetchError: Failure()
    }
}