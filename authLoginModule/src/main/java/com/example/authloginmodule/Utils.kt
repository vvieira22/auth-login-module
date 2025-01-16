package com.example.authloginmodule

import kotlin.reflect.KClass

class Utils {

    object Constants {
        private const val URL_API = "http://10.0.2.2"
        private const val API_PORT = ":8000"
        private const val URL_FULL = "$URL_API$API_PORT"

        const val LOGIN = "$URL_FULL/user/login/{type}"
        const val CADASTRO = "$URL_FULL/user/register/{type}"

        const val DEFAUT_LOGIN = "password"
        const val GOOGLE_LOGIN = "google"
        const val FACEBOOK_LOGIN = "facebook"
        const val BIOMETRIC_LOGIN = "biometric"

    }

    fun <T : Any> mapToObject(map: Map<String, Any>, clazz: KClass<T>): T {
        //Get default constructor
        val constructor = clazz.constructors.first()

        //Map constructor parameters to map values
        val args = constructor
            .parameters
            .map { it to map.get(it.name) }
            .toMap()

        //return object from constructor call
        return constructor.callBy(args)
    }
}