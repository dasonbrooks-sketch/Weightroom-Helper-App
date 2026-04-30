package com.example.weightroom_help

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

data class UsdaFood(
    val fdcId: Int? = null,
    val description: String? = null,
    val foodNutrients: List<UsdaNutrient>? = null
)

data class UsdaNutrient(
    val nutrientName: String? = null,
    val nutrientNumber: String? = null,
    val value: Double? = null,
    val unitName: String? = null
)

data class UsdaSearchResponse(
    val foods: List<UsdaFood>? = null
)

interface FoodApi {
    @GET("foods/search")
    suspend fun searchFood(
        @Query("query") query: String,
        @Query("api_key") apiKey: String = "UeBXVCQpRHfSI4ngASmhT9E7ygyUr1siXoMIXxAZ",
        @Query("pageSize") pageSize: Int = 10,
        @Query("dataType") dataType: String = "Foundation,SR Legacy,Branded"
    ): UsdaSearchResponse
}

object FoodApiClient {
    private val client = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .build()

    val api: FoodApi = Retrofit.Builder()
        .baseUrl("https://api.nal.usda.gov/fdc/v1/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(FoodApi::class.java)
}