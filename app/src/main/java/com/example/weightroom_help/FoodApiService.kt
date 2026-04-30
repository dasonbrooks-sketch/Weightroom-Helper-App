package com.example.weightroom_help

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

data class FoodProduct(
    val product_name: String? = null,
    val nutriments: Nutriments? = null
)

data class Nutriments(
    val energy_kcal_100g: Double? = null,
    val proteins_100g: Double? = null,
    val carbohydrates_100g: Double? = null,
    val fat_100g: Double? = null
)

data class FoodSearchResponse(
    val products: List<FoodProduct>? = null
)

interface FoodApi {
    @GET("cgi/search.pl")
    suspend fun searchFood(
        @Query("search_terms") query: String,
        @Query("json") json: Int = 1,
        @Query("page_size") pageSize: Int = 5
    ): FoodSearchResponse
}

object FoodApiClient {
    val api: FoodApi = Retrofit.Builder()
        .baseUrl("https://world.openfoodfacts.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(FoodApi::class.java)
}