package com.example.authloginmodule
import com.example.authloginmodule.Utils.Constants
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.Flow

interface AuthLibrary {
    fun register(body: JsonObject, type: String = Constants.DEFAUT_LOGIN): Flow<Result<RegisterResponse>>
    fun login(body: JsonObject, type: String = Constants.DEFAUT_LOGIN): Flow<Result<LoginResponse>>
}