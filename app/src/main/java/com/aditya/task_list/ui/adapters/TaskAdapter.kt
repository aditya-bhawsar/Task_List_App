package com.aditya.task_list.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aditya.task_list.R
import com.aditya.task_list.databinding.ItemTaskBinding
import com.aditya.task_list.data.TaskModel

//Adapter for Task
class TaskAdapter : RecyclerView.Adapter<TaskAdapter.MyViewHolder>() {

    //Callback for object to compare list
    private val differCallback = object : DiffUtil.ItemCallback<TaskModel>() {
        override fun areItemsTheSame(oldItem: TaskModel, newItem: TaskModel) = (oldItem === newItem)
        override fun areContentsTheSame(oldItem: TaskModel, newItem: TaskModel)= (oldItem == newItem)
    }

    //AsyncListDiffer for the Adapter
    val differ = AsyncListDiffer(this, differCallback)

    class MyViewHolder(private val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            //Method to Create ViewHolder
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding: ItemTaskBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_task , parent, false)
                return MyViewHolder(binding)
            }
        }

        //Binding the object
        fun bind(task: TaskModel) {
            //Setting the object in DataBinding and executing bindings
            binding.task = task
            binding.executePendingBindings()
        }
    }

    //Method Required to create new View Holders
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MyViewHolder.from(parent)

    //Method Required to bind View Holders
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) = holder.bind(differ.currentList[position])

    //Method Required to get List Size of Adapter
    override fun getItemCount() = differ.currentList.size

}