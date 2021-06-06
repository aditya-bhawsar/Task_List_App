package com.aditya.task_list.ui.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.aditya.task_list.R
import com.aditya.task_list.databinding.FragmentEditBinding
import com.aditya.task_list.data.TaskModel
import com.aditya.task_list.ui.activity.AppActivity
import com.aditya.task_list.util.Utils.parsePriority
import com.aditya.task_list.util.Utils.verifyData
import com.aditya.task_list.viewModel.EditViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
//Edit Fragment
class EditFragment : Fragment(R.layout.fragment_edit) {

    //Argument for the destination
    private val args by navArgs<EditFragmentArgs>()
    //ViewModel For the Screen
    private val mEditViewModel: EditViewModel by viewModels()

    //Binding object for the Screen
    private var _binding: FragmentEditBinding? = null
    private val binding
        get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Binding the View
        _binding = FragmentEditBinding.bind(view)

        binding.apply {
            //Setting Args in Binding Object
            args = this@EditFragment.args
            //Setting OnCLick
            saveFab.setOnClickListener { updateItem() }
        }

        //Allow Options and Back Button in Action Bar
        setHasOptionsMenu(true)
        (requireActivity() as AppActivity).supportActionBar!!.setDisplayShowHomeEnabled(false)
        (requireActivity() as AppActivity).supportActionBar!!.setIcon(null)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        //Inflating the menu
        inflater.inflate(R.menu.edit_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //Option Selection
        when (item.itemId) {
            R.id.menu_delete -> confirmItemRemoval()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun confirmItemRemoval() {
        //Conformation Dialog To delete Item
        MaterialAlertDialogBuilder(requireContext())
            .setPositiveButton("Yes") { _, _ ->
                //Deleting Task
                mEditViewModel.deleteData(args.Task)
                Toast.makeText(
                    requireContext(),
                    "Successfully Removed: '${args.Task.title}'",
                    Toast.LENGTH_SHORT
                ).show()
                //Navigate to destination
                findNavController().navigate(R.id.action_editFragment_to_listingFragment)
            }
            .setNegativeButton("No") { _, _ -> }
            .setTitle("Delete '${args.Task.title}'?")
            .setMessage("Are you sure you want to remove '${args.Task.title}'?")
            .create()
            .show()
    }

    private fun updateItem() {
        //Getting Data from Views
        val title = binding.titleEt.text.toString()
        val desc = binding.descEt.text.toString()
        val priority = binding.optionDropdown.text.toString()

        //Validating
        val validation = verifyData(title, desc, priority)
        if (validation) {
            //Creating and updating the objects in Database
            val updatedItem = TaskModel(args.Task.id, title, desc, parsePriority(priority))
            mEditViewModel.updateData(updatedItem)
            Toast.makeText(requireContext(), "Updated Successfully", Toast.LENGTH_LONG).show()
            //Navigating Back
            findNavController().navigate(R.id.action_editFragment_to_listingFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //Clearing reference to the view
        _binding = null
    }
}