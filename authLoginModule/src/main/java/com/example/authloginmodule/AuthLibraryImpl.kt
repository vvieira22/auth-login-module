package com.example.authloginmodule

import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class AuthLibraryImpl(url: String) : AuthLibrary {
    private val endpoint = NetworkUtils.getRetrofitInstance(url)

    override fun register(body: JsonObject, type: String): Flow<Result<RegisterResponse>> = flow {
        try {
            val registerResponse = suspendCancellableCoroutine { continuation ->
                endpoint.register(type, body).enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        if (response.isSuccessful) {
                            val jsonResponse = response.body()
                            if (response.code() == 201) {
                                continuation.resume(RegisterResponse(jsonResponse.toString()))
                            }
                        } else {
                            val errorBody = response.errorBody()?.string()
                            val errorMessage = errorBody ?: "Unknown error"
                            continuation.resumeWithException(Exception("Register user failed: status code: ${response.code()} - Body: $errorMessage"))
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        continuation.resumeWithException(Exception("Register user failed: ${t.message}"))
                    }
                })
                continuation.invokeOnCancellation {
                    endpoint.register(type, body).cancel()
                }
            }
            emit(Result.success(registerResponse))
        } catch (e: Exception) {
            emit(Result.failure(Exception("Register user failed: ${e.message}")))
        }
    }.flowOn(Dispatchers.IO)

    override fun login(body: JsonObject, type: String): Flow<Result<LoginResponse>> = flow {
        try {
            val loginResponse = suspendCancellableCoroutine { continuation ->
                endpoint.login(type = type, usr = body).enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        if (response.isSuccessful) {
                            val responseBody = response.body()
                            if(responseBody != null) {
                                if (response.code() == 200) {
                                    continuation.resume(LoginResponse("token", responseBody))
                                }
                            }
                            else{
                                continuation.resumeWithException(Exception("login failed: status code: ${response.code()} - Body: $responseBody"))
                            }
                        } else {
                            val errorBody = response.errorBody()?.string()
                            val errorMessage = errorBody ?: "Unknown error"
                            continuation.resumeWithException(Exception("login failed: status code: ${response.code()} - Body: $errorMessage"))
                        }
                    }
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        continuation.resumeWithException(Exception("login user failed: ${t.message}"))
                    }
                })
                continuation.invokeOnCancellation {
                    endpoint.login(type, body).cancel()
                }
            }
            emit(Result.success(loginResponse))
        } catch (e: Exception) {
            emit(Result.failure(Exception("Register user failed: ${e.message}")))
        }
    }.flowOn(Dispatchers.IO)

}