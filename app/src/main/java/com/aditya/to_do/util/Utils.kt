package com.aditya.to_do.util

import com.aditya.to_do.model.Priority

object Utils {

    const val DATABASE_NAME = "DATABASE_NAME"

    fun parsePriority(priority: String): Priority {
        return when(priority){
            "High Priority"->{
                Priority.HIGH}
            "Medium Priority"->{
                Priority.MEDIUM}
            "Low Priority"->{
                Priority.LOW}
            else -> Priority.LOW
        }
    }

    fun verifyData(title: String, desc: String, priority: String):Boolean{
        return !(title.isEmpty() || desc.isEmpty() || priority.isEmpty())
    }
}