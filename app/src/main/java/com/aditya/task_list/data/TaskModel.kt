package com.aditya.task_list.data

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
//Data Class With Parcelize (Parcel the Object),
//Entity (Creates Table in Db for the Data class)
//and
//Keep (Safe from obfuscation in pro guard)