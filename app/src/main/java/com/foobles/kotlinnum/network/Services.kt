package com.foobles.kotlinnum.network

import retrofit2.http.GET

 const val BASE_URL =
    "http://numbersapi.com/"
interface Services {

    @GET("/random/math?json")
    suspend fun randomNumberTrivia(): String
}