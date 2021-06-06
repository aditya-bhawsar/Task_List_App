package com.aditya.task_list.ui.adapters

import android.content.Context
import android.content.res.ColorStateList
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.aditya.task_list.R
import com.aditya.task_list.data.Priority
import com.aditya.task_list.data.TaskModel
import com.aditya.task_list.ui.fragments.ListingFragmentDirections
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

//Binding adapters for the ui elements
class BindingAdapters {

    companion object {

        //Implementation of ExtendedFloatingActionButton on ListingFragment
        @BindingAdapter("android:navigateToAddFragment")
        @JvmStatic
        fun navigateToAddFragment(fab: ExtendedFloatingActionButton, navigate: Boolean) {
            //Setting OnCLick
            fab.setOnClickListener {
                if (navigate) {
                    //Navigating according to controller
                    fab.findNavController().navigate(R.id.action_listingFragment_to_newFragment)
                }
            }
        }

        //Observing LiveData of list of task from adapter
        @BindingAdapter("android:emptyDatabase")
        @JvmStatic
        fun emptyDatabase(view: View, emptyDatabase: MutableLiveData<Boolean>) {
            //Setting View Visible according to de LiveData
            when (emptyDatabase.value) {
                true -> view.visibility = View.VISIBLE
                false -> view.visibility = View.INVISIBLE
            }
        }

        //Implementation of OnCLick of ListingFragment RecyclerView
        @BindingAdapter("android:sendDataToUpdateFragment")
        @JvmStatic
        fun sendDataToUpdateFragment(view: ConstraintLayout, currentItem: TaskModel) {
            //Setting OnCLick
            view.setOnClickListener {
                //Setting Arguments
                val action = ListingFragmentDirections.actionListingFragmentToEditFragment(
                    currentItem
                )
                //Navigating with controller
                view.findNavController().navigate(action)
            }
        }

        //Converting Priority To Display
        @BindingAdapter("android:parsePriorityToText")
        @JvmStatic
        fun parsePriorityToText(view: AutoCompleteTextView, priority: Priority) {
            //Setting Text According to the Priority
            when (priority) {
                Priority.HIGH -> {
                    view.setText(view.context.getString(R.string.high_priority))
                }
                Priority.MEDIUM -> {
                    view.setText(view.context.getString(R.string.medium_priority))
                }
                Priority.LOW -> {
                    view.setText(view.context.getString(R.string.low_priority))
                }
            }
        }

        //Setting Color based on Priority
        @BindingAdapter("android:parsePriorityColor")
        @JvmStatic
        fun parsePriorityColor(view: ImageView, priority: Priority) {
            //Setting Color on ImageView According to the Priority
            when (priority) {
                Priority.HIGH -> {
                    view.imageTintList = ColorStateList.valueOf(
                        ContextCompat.getColor(
                            view.context,
                            R.color.colorRed
                        )
                    )
                }
                Priority.MEDIUM -> {
                    view.imageTintList = ColorStateList.valueOf(
                        ContextCompat.getColor(
                            view.context,
                            R.color.colorYellow
                        )
                    )
                }
                Priority.LOW -> {
                    view.imageTintList = ColorStateList.valueOf(
                        ContextCompat.getColor(
                            view.context,
                            R.color.colorGreen
                        )
                    )
                }
            }
        }
    }

}