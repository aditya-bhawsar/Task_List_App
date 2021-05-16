package com.aditya.to_do.ui.adapters

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
import com.aditya.to_do.R
import com.aditya.to_do.model.Priority
import com.aditya.to_do.model.TaskModel
import com.aditya.to_do.ui.fragments.ListingFragmentDirections
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class BindingAdapters {

    companion object{

        @BindingAdapter("android:allowInput")
        @JvmStatic
        fun allowInput(autoCompleteTextView: AutoCompleteTextView, show: Boolean){
            autoCompleteTextView.isCursorVisible = show
            autoCompleteTextView.showSoftInputOnFocus = show

            val options: Array<String> = autoCompleteTextView.context.resources.getStringArray(R.array.priorities)
            val adapter: ArrayAdapter<String> = ArrayAdapter(autoCompleteTextView.context, R.layout.dropdown_item, options)
            autoCompleteTextView.setAdapter(adapter)

            autoCompleteTextView.setOnFocusChangeListener { v, hasFocus ->
                if(hasFocus){
                    val imm = v.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.windowToken, 0)
                }
            }
        }

        @BindingAdapter("android:navigateToAddFragment")
        @JvmStatic
        fun navigateToAddFragment(
            floatingActionButton: ExtendedFloatingActionButton,
            navigate: Boolean
        ){
            floatingActionButton.setOnClickListener {
                if(navigate){floatingActionButton.findNavController().navigate(R.id.action_listingFragment_to_newFragment)}
            }
        }

        @BindingAdapter("android:emptyDatabase")
        @JvmStatic
        fun emptyDatabase(view: View, emptyDatabase: MutableLiveData<Boolean>){
            when (emptyDatabase.value){
                true -> view.visibility = View.VISIBLE
                false -> view.visibility = View.INVISIBLE
            }
        }

        @BindingAdapter("android:sendDataToUpdateFragment")
        @JvmStatic
        fun sendDataToUpdateFragment(view: ConstraintLayout, currentItem: TaskModel){
            view.setOnClickListener {
                val action = ListingFragmentDirections.actionListingFragmentToEditFragment(
                    currentItem
                )
                view.findNavController().navigate(action)
            }
        }

        @BindingAdapter("android:parsePriorityToText")
        @JvmStatic
        fun parsePriorityToText(view: AutoCompleteTextView, priority: Priority){
            when(priority){
                Priority.HIGH -> { view.setText("High Priority") }
                Priority.MEDIUM -> { view.setText("Medium Priority") }
                Priority.LOW -> { view.setText("Low Priority") }
            }
        }

        @BindingAdapter("android:parsePriorityColor")
        @JvmStatic
        fun parsePriorityColor(view:ImageView, priority: Priority){
            when(priority){
                Priority.HIGH -> {
                    view.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(
                        view.context,
                        R.color.colorRed
                    ))
                }
                Priority.MEDIUM -> {
                    view.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(
                        view.context,
                        R.color.colorYellow
                    ))
                }
                Priority.LOW -> {
                    view.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(
                        view.context,
                        R.color.colorGreen
                    ))
                }
            }
        }
    }

}