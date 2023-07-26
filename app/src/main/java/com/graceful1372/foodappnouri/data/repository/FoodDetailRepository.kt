package com.graceful1372.foodappnouri.data.repository

import com.graceful1372.foodappnouri.data.model.ResponseFoodsList
import com.graceful1372.foodappnouri.data.database.FoodDao
import com.graceful1372.foodappnouri.data.database.FoodEntity
import com.graceful1372.foodappnouri.data.server.ApiServices
import com.graceful1372.foodappnouri.utlis.MyResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FoodDetailRepository @Inject constructor(
    private val apiServices: ApiServices,
    private val dao: FoodDao
) {
    suspend fun foodDetail(id: Int): Flow<MyResponse<ResponseFoodsList>> {
        return flow {
            emit(MyResponse.loading())
            when (apiServices.foodDetail(id).code()) {
                in 200..202 -> {
                    emit(MyResponse.success(apiServices.foodDetail(id).body()))
                }
            }
        }.catch {
            emit(MyResponse.error(it.message.toString()))
        }
            .flowOn(Dispatchers.IO)
    }

    suspend fun saveFood(entity: FoodEntity) = dao.saveFood(entity)
    suspend fun deleteFood(entity: FoodEntity) = dao.deleteFood(entity)
    fun existsFood(id: Int) = dao.existsFood(id)

}