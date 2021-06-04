package com.aditya.to_do.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [TaskModel::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getTaskDao(): TaskDao
}