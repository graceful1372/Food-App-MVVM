package com.graceful1372.foodappnouri.data.repository

import com.graceful1372.foodappnouri.data.model.ResponseCategoriesList
import com.graceful1372.foodappnouri.data.model.ResponseFoodsList
import com.graceful1372.foodappnouri.data.server.ApiServices
import com.graceful1372.foodappnouri.utlis.MyResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

class FoodListRepository @Inject constructor(private val apiServices: ApiServices) {


    // این ای پی ای رو با استفاده از حالت عادی استفاده میکنیم
    suspend fun randomFood(): Flow<Response<ResponseFoodsList>> {
        return flow {
            emit(apiServices.foodRandom())
        }.flowOn(Dispatchers.IO) //تعینن این که در کدام theard استفاده میشده
    }

    // بقیه ای پی ها رو با استفاده از کلاس myresponse  استفاده میکنیم
    suspend fun categoriesList(): Flow<MyResponse<ResponseCategoriesList>> {
        return flow {
            emit(MyResponse.loading())
            //Response
            when (apiServices.categoriesList().code()) {
                in 200..202 -> {
                    emit(MyResponse.success(apiServices.categoriesList().body()))
                }

                422 -> {
                    emit(MyResponse.error(""))
                }

                in 400..499 -> {
                    emit(MyResponse.error(""))
                }

                in 500..599 -> {
                    emit(MyResponse.error(""))
                }
            }

        }.catch {
            emit(MyResponse.error(it.message.toString()))
        }
            .flowOn(Dispatchers.IO)
    }

    suspend fun foodList(letter: String): Flow<MyResponse<ResponseFoodsList>> {
        return flow {
            //Loading
            emit(MyResponse.loading())
            //Response
            when (apiServices.foodsList(letter).code()) {
                in 200..202 -> {
                    emit(MyResponse.success(apiServices.foodsList(letter).body()))
                }
            }

        }.catch {
            emit(MyResponse.error(it.message.toString()))
        }
            .flowOn(Dispatchers.IO)
    }

    suspend fun foodBySearch(letter: String): Flow<MyResponse<ResponseFoodsList>> {
        return flow {
            //Loading
            emit(MyResponse.loading())
            //Response
            when (apiServices.searchFood(letter).code()) {
                in 200..202 -> {
                    emit(MyResponse.success(apiServices.searchFood(letter).body()))
                }
            }

        }.catch {
            emit(MyResponse.error(it.message.toString()))
        }
            .flowOn(Dispatchers.IO)
    }

    suspend fun foodByCategory(letter: String): Flow<MyResponse<ResponseFoodsList>> {
        return flow {
            //Loading
            emit(MyResponse.loading())
            //Response
            when (apiServices.foodsList(letter).code()) {
                in 200..202 -> {
                    emit(MyResponse.success(apiServices.foodsByCategory(letter).body()))
                }
            }

        }.catch {
            emit(MyResponse.error(it.message.toString()))
        }
            .flowOn(Dispatchers.IO)
    }
}