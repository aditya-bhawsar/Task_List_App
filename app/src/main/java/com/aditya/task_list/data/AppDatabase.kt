package com.aditya.task_list.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

//Defining Database
@Database(entities = [TaskModel::class], version = 1, exportSchema = false)
//Adding Type Converter for Priority
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase()
{
    //Method To get TaskDao
    abstract fun getTaskDao(): TaskDao
}