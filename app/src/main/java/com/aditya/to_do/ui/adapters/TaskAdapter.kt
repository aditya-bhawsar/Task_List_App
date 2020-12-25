package com.aditya.to_do.ui.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aditya.to_do.R
import com.aditya.to_do.databinding.RowLayoutBinding
import com.aditya.to_do.model.Priority
import com.aditya.to_do.model.TaskModel

class TaskAdapter: RecyclerView.Adapter<TaskAdapter.MyViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<TaskModel>(){
        override fun areItemsTheSame(oldItem: TaskModel, newItem: TaskModel): Boolean { return oldItem===newItem }
        override fun areContentsTheSame(oldItem: TaskModel, newItem: TaskModel): Boolean {
            return oldItem.id==newItem.id
                    && oldItem.title==newItem.title
                    && oldItem.description==newItem.description
                    && oldItem.priority==newItem.priority
        }
    }

    val differ = AsyncListDiffer(this,differCallback)

    class MyViewHolder(val binding: RowLayoutBinding): RecyclerView.ViewHolder(binding.root){
        companion object{
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RowLayoutBinding.inflate(layoutInflater,parent,false)
                return MyViewHolder(binding)
            }
        }

        fun bind(task: TaskModel){
            binding.task = task
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) { holder.bind(differ.currentList[position]) }

    override fun getItemCount(): Int = differ.currentList.size

}