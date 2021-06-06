package com.aditya.task_list.util

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.aditya.task_list.data.Priority

object Utils {

    const val DATABASE_NAME = "DATABASE_NAME"

    //Get String From Priority
    fun parsePriority(priority: String): Priority {
        return when (priority) {
            "High Priority" -> {
                Priority.HIGH
            }
            "Medium Priority" -> {
                Priority.MEDIUM
            }
            "Low Priority" -> {
                Priority.LOW
            }
            else -> Priority.LOW
        }
    }

    //Check Input Data From Screen to be a valid Task Object
    fun verifyData(title: String, desc: String, priority: String): Boolean {
        return !(title.isEmpty() || desc.isEmpty() || priority.isEmpty())
    }

    //Observes LiveData Only once
    fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
        observe(lifecycleOwner, object : Observer<T> {
            override fun onChanged(t: T) {
                observer.onChanged(t)
                removeObserver(this)
            }
        })
    }

}