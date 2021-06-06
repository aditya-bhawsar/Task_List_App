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
class EditViewModel @Inject constructor(private val appRepo: AppRepository) : ViewModel() {

    //Update Task in Database
    fun updateData(task: TaskModel) = viewModelScope.launch(Dispatchers.IO) { appRepo.updateData(task) }

    //Delete Task in Database
    fun deleteData(task: TaskModel) = viewModelScope.launch(Dispatchers.IO) { appRepo.deleteData(task) }

}