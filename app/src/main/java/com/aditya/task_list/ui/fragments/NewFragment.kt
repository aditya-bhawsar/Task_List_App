package com.aditya.task_list.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.aditya.task_list.R
import com.aditya.task_list.databinding.FragmentNewBinding
import com.aditya.task_list.data.TaskModel
import com.aditya.task_list.ui.activity.AppActivity
import com.aditya.task_list.util.Utils.parsePriority
import com.aditya.task_list.util.Utils.verifyData
import com.aditya.task_list.viewModel.NewViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewFragment : Fragment(R.layout.fragment_new) {

    //ViewModel for the Screen
    private val mNewViewModel: NewViewModel by viewModels()

    //Binding Object for the Screen
    private var _binding: FragmentNewBinding? = null
    private val binding
        get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Binding the View
        _binding = FragmentNewBinding.bind(view)

        binding.apply { saveFab.setOnClickListener { insertData() } }

        //Setting Up the Action Bar Navigation Back Button
        (requireActivity() as AppActivity).supportActionBar!!.setDisplayShowHomeEnabled(false)
        (requireActivity() as AppActivity).supportActionBar!!.setIcon(null)
    }

    private fun insertData() {
        //Getting Data from the Views
        val mTitle = binding.titleEt.text.toString()
        val mPriority = binding.optionDropdown.text.toString()
        val mDescription = binding.descEt.text.toString()

        //Validating the Data
        val validation = verifyData(mTitle, mDescription, mPriority)
        if (validation) {
            //Creating and Adding Object To Database
            val data = TaskModel(0, mTitle, mDescription, parsePriority(mPriority))
            mNewViewModel.insertData(data)
            Toast.makeText(requireContext(), "Added SuccessFully", Toast.LENGTH_LONG).show()
            //Navigating Back
            findNavController().navigate(R.id.action_newFragment_to_listingFragment)
        } else {
            Toast.makeText(requireContext(), "Please Fill all data", Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //Clearing Reference of the View
        _binding = null
    }
}