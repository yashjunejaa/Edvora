package com.example.myapplication

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

const val BASE_URL="https://assessment.api.vweb.app/";
interface RideInterface {

    @GET("rides")
    fun getRides() : Call<List<Rid>>

    @GET("user")
    fun getUser(): Call<User>
}

object RideService {
    val rideInstance: RideInterface
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        rideInstance = retrofit.create(RideInterface::class.java)
    }
}