package com.aditya.to_do.util

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
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

    fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
        observe(lifecycleOwner, object : Observer<T> {
            override fun onChanged(t: T) {
                observer.onChanged(t)
                removeObserver(this)
            }
        })
    }

}