package com.graceful1372.foodappnouri.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FoodEntity::class] , version = 1 , exportSchema = false)
abstract class FoodDatabase : RoomDatabase() {
    abstract fun foodDao():FoodDao

}