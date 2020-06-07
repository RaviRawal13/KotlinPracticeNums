package com.foobles.kotlinnum.network

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


/**
 * network manager to provide webservice and intercept the request/response.
 */
object NetworkManager {
    val retrofitService: Services by lazy {
        getRetrofit().create(Services::class.java)
    }

    private fun getRetrofit(): Retrofit {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)

            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
        return retrofit.build()
    }

}