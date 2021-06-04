package com.aditya.to_do.data

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Keep
@Entity(tableName = "task_table")
@Parcelize
data class TaskModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var title: String,
    var description: String,
    var priority: Priority
) : Parcelable