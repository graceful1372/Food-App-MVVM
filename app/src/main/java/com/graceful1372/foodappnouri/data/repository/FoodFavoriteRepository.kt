package com.graceful1372.foodappnouri.data.repository

import com.graceful1372.foodappnouri.data.database.FoodDao
import javax.inject.Inject

class FoodFavoriteRepository @Inject constructor(private  val dao: FoodDao) {

    fun foodsList()  = dao.getAllFood()
}