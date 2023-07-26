package com.graceful1372.foodappnouri.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.graceful1372.foodappnouri.utlis.FOOD_DB_TABLE


@Entity(tableName = FOOD_DB_TABLE)
data class FoodEntity(
    @PrimaryKey
    var id :Int = 0,
    var title:String = "",
    var img : String = ""
)
