package com.example.weightroom_help

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

//This part of the code represents a single food item that is returned from our USDA API
//Also all the fields are nullable because our API will not necessarily always return a value at every field
data class UsdaFood(
    val fdcId: Int? = null,
    val description: String? = null,
    val foodNutrients: List<UsdaNutrient>? = null
)
//This part of the code represents one nutrient entry inside the UsdaFood object
data class UsdaNutrient(
    val nutrientName: String? = null, //Name of the nutirent, ex. protein
    val nutrientNumber: String? = null, //This is the USDA nutrient code numebr
    val value: Double? = null, //This is the amount of said nutrient per 100g
    val unitName: String? = null //This is the unit of measurement that can be grams G, milligrams MG or kilocalories KCAL
)
//This part of the code returns the USDA endpoint return so itll return the food that is selected
data class UsdaSearchResponse(
    val foods: List<UsdaFood>? = null
)

interface FoodApi {
    //This aprt of the code maps to get https://api.nal.usda.gov/fdc/v1/foods/search
    @GET("foods/search")
    suspend fun searchFood( // this part does not actually suspends but runs a coroutine, it wont block the actual main important thread
        @Query("query") query: String, //this the food seaerch term that is entered by the user
        @Query("api_key") apiKey: String = "UeBXVCQpRHfSI4ngASmhT9E7ygyUr1siXoMIXxAZ", //this is usda api key, it is defaulted so the caller wont be needing to pass it
        @Query("pageSize") pageSize: Int = 10, //This is the maximum amount of results that it will return per request
        @Query("dataType") dataType: String = "Foundation,SR Legacy,Branded" //this goes on to filter the USDA dataset types
    ): UsdaSearchResponse
}

object FoodApiClient {
    private val client = OkHttpClient.Builder() // this part of the code prevents the app from staying on a slow ir failed request
        .connectTimeout(10, TimeUnit.SECONDS) //this is the max amount of time given to establish a connection
        .readTimeout(10, TimeUnit.SECONDS) //it is the max time that is needed to wait for the data to start arriving
        .writeTimeout(10, TimeUnit.SECONDS) //the max time to send a request
        .build()

    val api: FoodApi = Retrofit.Builder()
        .baseUrl("https://api.nal.usda.gov/fdc/v1/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(FoodApi::class.java)
}