package com.aditya.task_list.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDao {

    //Query to get All tasks in LiveData
    @Query("SELECT * FROM task_table ORDER BY id ASC")
    fun getAllData(): LiveData<List<TaskModel>>

    //Query to Add a new task
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(task: TaskModel)

    //Query to update tasks
    @Update
    suspend fun updateItem(task: TaskModel)

    //Query to delete a single task
    @Delete
    suspend fun deleteItem(task: TaskModel)

    //Query to delete All tasks
    @Query("DELETE FROM task_table")
    suspend fun deleteAll()

    //Query to get All tasks in LiveData that match the search query
    @Query("SELECT * FROM task_table WHERE title LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): LiveData<List<TaskModel>>

    //Query to get All tasks in LiveData sorted by priority High to Low
    @Query("SELECT * FROM task_table ORDER BY CASE WHEN priority LIKE 'H%' THEN 3 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'L%' THEN 1 END")
    fun sortByHighPriority(): LiveData<List<TaskModel>>

    //Query to get All tasks in LiveData sorted by priority Low to High
    @Query("SELECT * FROM task_table ORDER BY CASE WHEN priority LIKE 'L%' THEN 3 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'H%' THEN 1 END")
    fun sortByLowPriority(): LiveData<List<TaskModel>>

}