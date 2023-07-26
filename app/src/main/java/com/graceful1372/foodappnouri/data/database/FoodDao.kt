package com.graceful1372.foodappnouri.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.graceful1372.foodappnouri.utlis.FOOD_DB_TABLE
import kotlinx.coroutines.flow.Flow


@Dao
interface FoodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFood (entity: FoodEntity)

    @Delete
    suspend fun deleteFood(entity: FoodEntity)

    @Query(" SELECT * FROM $FOOD_DB_TABLE ")
    fun getAllFood():Flow<MutableList<FoodEntity>>

    @Query("SELECT EXISTS (SELECT 1 FROM $FOOD_DB_TABLE WHERE id =:id)")
    fun existsFood (id:Int) :Flow<Boolean>



}