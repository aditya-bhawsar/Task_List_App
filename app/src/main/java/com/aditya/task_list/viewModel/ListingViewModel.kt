package com.aditya.task_list.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aditya.task_list.data.TaskModel
import com.aditya.task_list.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListingViewModel @Inject constructor(private val appRepo: AppRepository) : ViewModel() {

    //Check if Database is Empty in Binding Object
    val emptyDatabase: MutableLiveData<Boolean> = MutableLiveData(false)
    //Check if database is Empty
    fun checkIfDatabaseEmpty(boolean: Boolean) { emptyDatabase.value = boolean }

    //All Tasks in LiveData
    val getAllData: LiveData<List<TaskModel>> = appRepo.getAllData()

    //All Tasks in LiveData according to Priority
    val highPriorityData: LiveData<List<TaskModel>> = appRepo.sortByHighPriority()
    val lowPriorityData: LiveData<List<TaskModel>> = appRepo.sortByLowPriority()

    //Add Task to Database
    fun insertData(task: TaskModel) = viewModelScope.launch(Dispatchers.IO) { appRepo.insertData(task) }

    //Search Database for tasks that match Query
    fun searchDatabase(searchQuery: String) = appRepo.searchDatabase(searchQuery)

    //Delete Task to Database
    fun deleteData(task: TaskModel) = viewModelScope.launch(Dispatchers.IO) { appRepo.deleteData(task) }

    //Delete All Tasks in Database
    fun deleteAll() = viewModelScope.launch(Dispatchers.IO) { appRepo.deleteAll() }
}