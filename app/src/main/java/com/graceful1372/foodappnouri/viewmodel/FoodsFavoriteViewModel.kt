package com.graceful1372.foodappnouri.viewmodel

import android.provider.ContactsContract.Data
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.graceful1372.foodappnouri.data.database.FoodEntity
import com.graceful1372.foodappnouri.data.repository.FoodFavoriteRepository
import com.graceful1372.foodappnouri.utlis.DataStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodsFavoriteViewModel @Inject constructor(private  val repository: FoodFavoriteRepository):ViewModel() {


    val favoriteListLiveData = MutableLiveData<DataStatus<List<FoodEntity>>>()
    fun loadFavorites() = viewModelScope.launch (Dispatchers.IO){
        repository.foodsList().collect{
            favoriteListLiveData.postValue(DataStatus.success(it , it .isEmpty() ))

        }
    }
}