package com.example.mvvmnew.functional

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mvvmnew.api.Api
import com.example.mvvmnew.api.ApiResult
import com.example.mvvmnew.vo.error.GithubError
import com.google.common.truth.Truth
import com.google.gson.Gson
import io.mockk.MockKAnnotations
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import retrofit2.HttpException
import retrofit2.Response

class ApiTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    private val gson: Gson = Gson()

    private lateinit var subject: Api

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        subject = Api(gson)
    }

    @Test
    fun testApiSuccess() {
        runBlocking {
            val actual = subject.connect {
                "Success"
            }

            Truth.assertThat(actual)
                .isEqualTo(ApiResult.Success("Success"))
        }
    }


    @Test
    fun testApiCancelByCoroutines() {
        runBlocking {
            val actual: ApiResult<String> = subject.connect {
                throw CancellationException()
            }

            Truth.assertThat(actual)
                .isEqualTo(ApiResult.Canceled)
        }
    }

    @Test
    fun testApiError() {
        runBlocking {
            val actual: ApiResult<String> = subject.connect {
                throw HttpException(
                    Response.error<String>(
                        400,
                        """
                            {
                              "code":"testDetail",
                              "displayMessage":"testDisplayMessage",
                              "instance":"testInstance",
                              "invalidParams":[
                                {
                                  "detail":"testInvalidDetail1",
                                  "params":"testInvalidParams1",
                                  "value":"testInvalidValue1"
                                }
                              ],
                              "status":250
                            }
                        """.trimIndent().toResponseBody("application/json".toMediaType())
                    )
                )
            }

            Truth.assertThat(actual)
                .isEqualTo(
                    ApiResult.Error.ApiError(
                        statusCode = 400,
                        githubError = GithubError(
                            code = "testDetail",
                            displayMessage = "testDisplayMessage",
                            instance = "testInstance",
                            invalidParams = listOf(
                                GithubError.InvalidParam(
                                    detail = "testInvalidDetail1",
                                    params = "testInvalidParams1",
                                    value = "testInvalidValue1"
                                )
                            ),
                            status = 250
                        )
                    )
                )
        }
    }

    @Test
    fun testApiException() {
        runBlocking {
            val e = RuntimeException("testException")
            val actual: ApiResult<String> = subject.connect {
                throw e
            }

            Truth.assertThat(actual)
                .isEqualTo(
                    ApiResult.Error.Exception(e)
                )
        }
    }

}