package com.aditya.to_do.viewModel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aditya.to_do.model.TaskModel
import com.aditya.to_do.repository.AppRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListingViewModel @ViewModelInject constructor(private val appRepo: AppRepository): ViewModel() {

    val emptyDatabase: MutableLiveData<Boolean> = MutableLiveData(false)
    fun checkIfDatabaseEmpty(boolean: Boolean){ emptyDatabase.value = boolean }

    val getAllData: LiveData<List<TaskModel>> = appRepo.getAllData()
    val highPriorityData: LiveData<List<TaskModel>> = appRepo.sortByHighPriority()
    val lowPriorityData: LiveData<List<TaskModel>> = appRepo.sortByLowPriority()

    fun insertData(task: TaskModel) = viewModelScope.launch(Dispatchers.IO) { appRepo.insertData(task) }
    fun searchDatabase(searchQuery: String) = appRepo.searchDatabase(searchQuery)
    fun deleteData(task: TaskModel) = viewModelScope.launch(Dispatchers.IO) { appRepo.deleteData(task) }
    fun deleteAll() = viewModelScope.launch(Dispatchers.IO) { appRepo.deleteAll() }

}