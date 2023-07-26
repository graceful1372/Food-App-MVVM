package com.graceful1372.foodappnouri.utlis.di


import android.content.Context
import androidx.room.Room
import com.graceful1372.foodappnouri.data.database.FoodDatabase
import com.graceful1372.foodappnouri.data.database.FoodEntity
import com.graceful1372.foodappnouri.utlis.FOOD_DB_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {


    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, FoodDatabase::class.java, FOOD_DB_DATABASE
    ).allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()


    @Provides
    @Singleton
    fun provideEntity() = FoodEntity()


    @Provides
    @Singleton
    fun provideDao(db: FoodDatabase) = db.foodDao()


}