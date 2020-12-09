package com.aditya.to_do.viewModel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aditya.to_do.model.TaskModel
import com.aditya.to_do.repository.AppRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewViewModel @ViewModelInject constructor(private val appRepo: AppRepository): ViewModel() {

    fun insertData(task: TaskModel) = viewModelScope.launch(Dispatchers.IO) { appRepo.insertData(task) }

}