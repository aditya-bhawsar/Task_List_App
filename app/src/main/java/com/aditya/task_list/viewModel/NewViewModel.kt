package com.aditya.task_list.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aditya.task_list.data.TaskModel
import com.aditya.task_list.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewViewModel @Inject constructor(private val appRepo: AppRepository) : ViewModel() {

    //Method To Insert Data from screen to Database
    fun insertData(task: TaskModel) = viewModelScope.launch(Dispatchers.IO) { appRepo.insertData(task) }

}