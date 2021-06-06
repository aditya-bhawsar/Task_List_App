package com.aditya.task_list.data

import androidx.room.TypeConverter

//Converter of data types
class Converter {

    //Convert from Priority to String
    @TypeConverter
    fun fromPriority(priority: Priority): String {
        return priority.name
    }

    //Convert from String to Priority
    @TypeConverter
    fun toPriority(string: String): Priority {
        return Priority.valueOf(string)
    }
}