package com.aditya.to_do.model

import androidx.room.TypeConverter

class Converter {

    @TypeConverter
    fun fromPriority(priority: Priority): String {
        return priority.name
    }

    @TypeConverter
    fun toPriority(string: String): Priority {
        return Priority.valueOf(string)
    }
}