package com.aditya.to_do.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDao {

    @Query("SELECT * FROM task_table ORDER BY id ASC")
    fun getAllData(): LiveData<List<TaskModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(task: TaskModel)

    @Update
    suspend fun updateItem(task: TaskModel)

    @Delete
    suspend fun deleteItem(task: TaskModel)

    @Query("DELETE FROM task_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM task_table WHERE title LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): LiveData<List<TaskModel>>

    @Query("SELECT * FROM task_table ORDER BY CASE WHEN priority LIKE 'H%' THEN 3 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'L%' THEN 1 END")
    fun sortByHighPriority(): LiveData<List<TaskModel>>

    @Query("SELECT * FROM task_table ORDER BY CASE WHEN priority LIKE 'L%' THEN 3 WHEN priority LIKE 'M%' THEN 2 WHEN priority LIKE 'H%' THEN 1 END")
    fun sortByLowPriority(): LiveData<List<TaskModel>>

}