package com.aditya.to_do.repository

import androidx.lifecycle.LiveData
import com.aditya.to_do.model.TaskDao
import com.aditya.to_do.model.TaskModel
import javax.inject.Inject

class AppRepository @Inject constructor(private val taskDao: TaskDao) {

    fun getAllData() = taskDao.getAllData()
    fun sortByLowPriority() = taskDao.sortByHighPriority()
    fun sortByHighPriority() = taskDao.sortByLowPriority()

    fun searchDatabase(searchQuery: String): LiveData<List<TaskModel>> = taskDao.searchDatabase(searchQuery)
    suspend fun insertData(toDoData: TaskModel) = taskDao.insert(toDoData)
    suspend fun updateData(toDoData: TaskModel) = taskDao.updateItem(toDoData)
    suspend fun deleteData(toDoData: TaskModel) = taskDao.deleteItem(toDoData)
    suspend fun deleteAll() = taskDao.deleteAll()

}