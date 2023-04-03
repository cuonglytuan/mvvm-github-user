package com.example.mvvmnew.vo.error

data class GithubError(val code: String,
                       val instance: String,
                       val status: Int,
                       val displayMessage: String?,
                       val invalidParams: List<InvalidParam>?,
) {
    data class InvalidParam(
        val detail: String?,
        val params: String?,
        val value: String?,
    )
}
