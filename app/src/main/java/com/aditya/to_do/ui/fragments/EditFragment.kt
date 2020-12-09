package com.aditya.to_do.ui.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.aditya.to_do.R
import com.aditya.to_do.databinding.FragmentEditBinding
import com.aditya.to_do.model.TaskModel
import com.aditya.to_do.util.Utils.parsePriority
import com.aditya.to_do.util.Utils.verifyData
import com.aditya.to_do.viewModel.EditViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditFragment : Fragment(R.layout.fragment_edit) {

    private val mEditViewModel: EditViewModel by viewModels()
    private val args by navArgs<EditFragmentArgs>()

    private var _binding : FragmentEditBinding? = null
    private val binding
        get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentEditBinding.bind(view)

        binding.apply { args = this@EditFragment.args }

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) { inflater.inflate(R.menu.update_fragment_menu,menu) }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.menu_save->updateItem()
            R.id.menu_delete->confirmItemRemoval()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun confirmItemRemoval() {
        MaterialAlertDialogBuilder(requireContext())
            .setPositiveButton("Yes"){_,_->
                mEditViewModel.deleteData(args.Task)
                Toast.makeText(requireContext(),"Successfully Removed: '${args.Task.title}'", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_editFragment_to_listingFragment)
            }
            .setNegativeButton("No"){_,_->}
            .setTitle("Delete '${args.Task.title}'?")
            .setMessage("Are you sure you want to remove '${args.Task.title}'?")
            .create().show()
    }

    private fun updateItem() {
        val title = binding.titleEt.text.toString()
        val desc = binding.descEt.text.toString()
        val priority = binding.optionDropdown.text.toString()

        val validation = verifyData(title,desc,priority)
        if(validation){
            val updatedItem = TaskModel(
                args.Task.id,
                title,
                desc,
                parsePriority(priority)
            )
            mEditViewModel.updateData(updatedItem)
            Toast.makeText(requireContext(),"Updated Successfully", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_editFragment_to_listingFragment)
        }else{ Toast.makeText(requireContext(),"Please fill all fields", Toast.LENGTH_LONG).show() }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}