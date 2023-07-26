package com.graceful1372.foodappnouri.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Entity
import com.graceful1372.foodappnouri.data.database.FoodEntity
import com.graceful1372.foodappnouri.data.model.ResponseFoodsList
import com.graceful1372.foodappnouri.data.repository.FoodDetailRepository
import com.graceful1372.foodappnouri.utlis.MyResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FoodDetailViewModel @Inject constructor(private val repository: FoodDetailRepository) :
    ViewModel() {

    val foodDetailLiveData = MutableLiveData<MyResponse<ResponseFoodsList>>()

    fun loadFoodDetail(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.foodDetail(id).collect { foodDetailLiveData.postValue(it) }
    }

    fun saveFood(entity: FoodEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.saveFood(entity)
    }

    fun deleteFood(entity: FoodEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteFood(entity)
    }


    val isFavoriteLiveData = MutableLiveData<Boolean>()
    fun existsFood(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.existsFood(id).collect {
            isFavoriteLiveData.postValue(it)

        }
    }
}