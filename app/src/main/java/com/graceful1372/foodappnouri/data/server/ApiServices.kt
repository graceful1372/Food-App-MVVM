package com.graceful1372.foodappnouri.data.server

import com.graceful1372.foodappnouri.data.model.ResponseCategoriesList
import com.graceful1372.foodappnouri.data.model.ResponseFoodsList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {
    @GET("random.php")
    suspend fun foodRandom(): Response<ResponseFoodsList>

    @GET("categories.php")
    suspend fun categoriesList(): Response<ResponseCategoriesList>

    @GET("search.php")
    suspend fun foodsList(@Query("f") letter: String): Response<ResponseFoodsList>

    @GET("search.php")
    suspend fun searchFood(@Query("s") letter: String): Response<ResponseFoodsList>

    @GET("filter.php")
    suspend fun foodsByCategory(@Query("c") letter: String): Response<ResponseFoodsList>

    @GET("lookup.php")
    suspend fun foodDetail(@Query("i") id: Int): Response<ResponseFoodsList>
}