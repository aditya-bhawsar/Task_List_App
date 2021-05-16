package com.aditya.to_do.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.aditya.to_do.R
import com.aditya.to_do.databinding.FragmentNewBinding
import com.aditya.to_do.model.TaskModel
import com.aditya.to_do.ui.activity.AppActivity
import com.aditya.to_do.util.Utils.parsePriority
import com.aditya.to_do.util.Utils.verifyData
import com.aditya.to_do.viewModel.NewViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewFragment : Fragment(R.layout.fragment_new) {

    private val mNewViewModel: NewViewModel by viewModels()

    private var _binding : FragmentNewBinding? = null
    private val binding
        get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentNewBinding.bind(view)

        binding.apply { saveFab.setOnClickListener { insertData() } }


        (activity as AppActivity).supportActionBar!!.setDisplayShowHomeEnabled(false)
        (activity as AppActivity).supportActionBar!!.setIcon(null)
    }

    private fun insertData() {
        val mTitle = binding.titleEt.text.toString()
        val mPriority = binding.optionDropdown.text.toString()
        val mDescription = binding.descEt.text.toString()

        val validation = verifyData(mTitle,mDescription,mPriority)
        if(validation){
            val data = TaskModel(
                0,
                mTitle,
                mDescription,
                parsePriority(mPriority)
            )
            mNewViewModel.insertData(data)
            Toast.makeText(requireContext(),"Added SuccessFully", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_newFragment_to_listingFragment)
        }else{ Toast.makeText(requireContext(),"Please Fill all data", Toast.LENGTH_LONG).show() }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}