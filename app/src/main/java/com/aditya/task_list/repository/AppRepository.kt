package com.aditya.task_list.repository

import androidx.lifecycle.LiveData
import com.aditya.task_list.data.TaskDao
import com.aditya.task_list.data.TaskModel
import javax.inject.Inject

class AppRepository @Inject constructor(private val taskDao: TaskDao) {

    //Task Data For Listing
    fun getAllData() = taskDao.getAllData()

    //Tasks sorted by priority
    fun sortByLowPriority() = taskDao.sortByHighPriority()
    fun sortByHighPriority() = taskDao.sortByLowPriority()

    //Task according to search query
    fun searchDatabase(searchQuery: String): LiveData<List<TaskModel>> = taskDao.searchDatabase(searchQuery)

    //Create, Update and Delete Operations
    suspend fun insertData(toDoData: TaskModel) = taskDao.insert(toDoData)
    suspend fun updateData(toDoData: TaskModel) = taskDao.updateItem(toDoData)
    suspend fun deleteData(toDoData: TaskModel) = taskDao.deleteItem(toDoData)
    suspend fun deleteAll() = taskDao.deleteAll()
}