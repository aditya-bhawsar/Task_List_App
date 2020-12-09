package com.aditya.to_do.ui.adapters

import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.Spinner
import androidx.cardview.widget.CardView
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
            autoCompleteTextView.showSoftInputOnFocus = show
        }

        @BindingAdapter("android:navigateToAddFragment")
        @JvmStatic
        fun navigateToAddFragment(floatingActionButton: ExtendedFloatingActionButton, navigate:Boolean){
            floatingActionButton.setOnClickListener {
                if(navigate){floatingActionButton.findNavController().navigate(R.id.action_listingFragment_to_newFragment)}
            }
        }

        @BindingAdapter("android:emptyDatabase")
        @JvmStatic
        fun emptyDatabase(view: View, emptyDatabase: MutableLiveData<Boolean>){
            when (emptyDatabase.value){
                true-> view.visibility = View.VISIBLE
                false-> view.visibility = View.INVISIBLE
            }
        }

        @BindingAdapter("android:sendDataToUpdateFragment")
        @JvmStatic
        fun sendDataToUpdateFragment(view: ConstraintLayout, currentItem: TaskModel){
            view.setOnClickListener {
                val action = ListingFragmentDirections.actionListingFragmentToEditFragment(currentItem)
                view.findNavController().navigate(action)
            }
        }

        @BindingAdapter("android:parsePriorityToInt")
        @JvmStatic
        fun parsePriorityToInt(view: Spinner, priority: Priority){
            when(priority){
                Priority.HIGH -> { view.setSelection(0) }
                Priority.MEDIUM -> { view.setSelection(1) }
                Priority.LOW-> { view.setSelection(2) }
            }
        }

        @BindingAdapter("android:parsePriorityColor")
        @JvmStatic
        fun parsePriorityColor(cardView: CardView, priority: Priority){
            when(priority){
                Priority.HIGH->{ cardView.setCardBackgroundColor(
                    ContextCompat.getColor(cardView.context,
                        R.color.colorPrimaryDark))}
                Priority.MEDIUM->{ cardView.setCardBackgroundColor(
                    ContextCompat.getColor(cardView.context,
                        R.color.colorPrimary))}
                Priority.LOW->{ cardView.setCardBackgroundColor(
                    ContextCompat.getColor(cardView.context,
                        R.color.colorAccent))}
            }
        }
    }

}