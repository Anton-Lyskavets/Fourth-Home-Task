package com.example.fourth_home_task.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL =
    "https://belarusbank.by/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface BankApiService {
    @GET("api/atm?city=Гомель")
    suspend fun getATM(): List<BankATM>
}

object BankApi {
    val retrofitService: BankApiService by lazy {
        retrofit.create(BankApiService::class.java)
    }
}