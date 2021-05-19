package com.aditya.to_do.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aditya.to_do.model.TaskModel
import com.aditya.to_do.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(private val appRepo: AppRepository) : ViewModel() {

    fun updateData(task: TaskModel) =
        viewModelScope.launch(Dispatchers.IO) { appRepo.updateData(task) }

    fun deleteData(task: TaskModel) =
        viewModelScope.launch(Dispatchers.IO) { appRepo.deleteData(task) }

}