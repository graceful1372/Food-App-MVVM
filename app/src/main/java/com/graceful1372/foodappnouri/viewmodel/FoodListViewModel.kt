package com.graceful1372.foodappnouri.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.graceful1372.foodappnouri.data.model.ResponseCategoriesList
import com.graceful1372.foodappnouri.data.model.ResponseFoodsList
import com.graceful1372.foodappnouri.data.repository.FoodListRepository
import com.graceful1372.foodappnouri.utlis.MyResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodListViewModel @Inject constructor(private val repository: FoodListRepository) :
    ViewModel() {



    init {
        loadFoodRandom()
        loadCategoriesList()
        loadFoodList("A")
    }

    //Random food
    val randomFoodLiveData = MutableLiveData<List<ResponseFoodsList.Meal>>()
    fun loadFoodRandom() = viewModelScope.launch(Dispatchers.IO) {
        repository.randomFood().collect {
            randomFoodLiveData.postValue(it.body()?.meals!!)
        }
    }

    //List for spinner
    val filterListLiveData = MutableLiveData<MutableList<Char>>()
    fun loadFilterList() = viewModelScope.launch(Dispatchers.IO) {
        val letters = listOf('A'..'Z').flatten().toMutableList()
        filterListLiveData.postValue(letters)
    }

    //Load categories List
    val categoriesListLiveData = MutableLiveData<MyResponse<ResponseCategoriesList>>()
    fun loadCategoriesList()= viewModelScope.launch(Dispatchers.IO) {
        repository.categoriesList().collect{
            categoriesListLiveData.postValue(it)
        }
    }

    //Foods
    val foodsListLiveData = MutableLiveData<MyResponse<ResponseFoodsList>>()
    fun loadFoodList(letter:String)= viewModelScope.launch(Dispatchers.IO){
        repository.foodList(letter).collect{
            foodsListLiveData.postValue(it)
        }
    }

    //Search
    fun loadFoodBySearch(letter:String)= viewModelScope.launch(Dispatchers.IO){
        repository.foodBySearch(letter).collect{
            foodsListLiveData.postValue(it)
        }
    }

    //Category
    fun loadFoodByCategory(letter:String)= viewModelScope.launch(Dispatchers.IO){
        repository.foodByCategory(letter).collect{
            foodsListLiveData.postValue(it)
        }
    }
}