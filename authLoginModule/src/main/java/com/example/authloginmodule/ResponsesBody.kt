package com.example.authloginmodule

import okhttp3.ResponseBody

data class RegisterResponse(val message: String)
data class LoginResponse(val message: String, val response: ResponseBody?)