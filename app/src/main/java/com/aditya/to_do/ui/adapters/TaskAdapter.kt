package com.aditya.to_do.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aditya.to_do.databinding.ItemTaskBinding
import com.aditya.to_do.data.TaskModel

class TaskAdapter : RecyclerView.Adapter<TaskAdapter.MyViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<TaskModel>() {
        override fun areItemsTheSame(oldItem: TaskModel, newItem: TaskModel) = (oldItem === newItem)
        override fun areContentsTheSame(oldItem: TaskModel, newItem: TaskModel)= (oldItem == newItem)
    }

    val differ = AsyncListDiffer(this, differCallback)

    class MyViewHolder(private val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemTaskBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }

        fun bind(task: TaskModel) {
            binding.task = task
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MyViewHolder.from(parent)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) = holder.bind(differ.currentList[position])
    override fun getItemCount() = differ.currentList.size

}