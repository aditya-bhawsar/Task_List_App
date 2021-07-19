package com.aditya.task_list.ui.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.aditya.task_list.R
import com.aditya.task_list.databinding.FragmentListingBinding
import com.aditya.task_list.data.TaskModel
import com.aditya.task_list.ui.activity.AppActivity
import com.aditya.task_list.ui.adapters.TaskAdapter
import com.aditya.task_list.util.Utils.observeOnce
import com.aditya.task_list.viewModel.ListingViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListingFragment : Fragment(R.layout.fragment_listing) {

    //ViewModel for the Screen
    private val mListingViewModel: ListingViewModel by viewModels()

    //Adapter for the recycler view
    private val adapter: TaskAdapter by lazy { TaskAdapter() }

    //Binding object for the Screen
    private var _binding: FragmentListingBinding? = null
    private val binding
        get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Binding the View
        _binding = FragmentListingBinding.bind(view)

        binding.apply {
            //Adding LifeCycleOwner to observe LiveData and Setting objects in Binding
            lifecycleOwner = viewLifecycleOwner
            viewModel = mListingViewModel

            //Adding adapter to RecyclerView
            listRv.adapter = adapter

            //Adding Swipe functionality to Delete
            swipeToDelete(listRv)
        }

        //Observing the data for the RecyclerView
        mListingViewModel.getAllData.observe(viewLifecycleOwner, { data ->
            mListingViewModel.checkIfDatabaseEmpty(data.isEmpty())
            adapter.differ.submitList(data)
            binding.listRv.scheduleLayoutAnimation()
        })

        //Allow Options And Icon in the Action Bar
        setHasOptionsMenu(true)

        (requireActivity() as AppActivity).supportActionBar!!.setDisplayShowHomeEnabled(true)
        (requireActivity() as AppActivity).supportActionBar!!.setIcon(R.drawable.ic_to_do)
    }

    private fun swipeToDelete(listRv: RecyclerView) {
        //Callback implementation of the ItemTouchHelper.SimpleCallback
        val swipeToDelete = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            )= false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                //Getting the item and deleting the item
                val itemToDelete = adapter.differ.currentList[viewHolder.adapterPosition]
                mListingViewModel.deleteData(itemToDelete)
                //Notifying the Adapter
                adapter.notifyItemRemoved(viewHolder.adapterPosition)
                //Showing Message and Allowing Restore of Item
                restoreDeletedItem(listRv, itemToDelete)
            }
        }
        //Creating and Adding ItemTouchHelper to the RecyclerView
        val itemTouchHelper = ItemTouchHelper(swipeToDelete)
        itemTouchHelper.attachToRecyclerView(listRv)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        //Crating Options Menu
        inflater.inflate(R.menu.list_fragment_menu, menu)
        //Creating the Action Of Search and Setting OnQueryChange Listener
        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) searchThroughDatabase(query)
                return true
            }
            override fun onQueryTextChange(query: String?): Boolean {
                if (query != null) searchThroughDatabase(query)
                return true
            }
        })
    }

    //Getting Data In One Shot from Database for the query
    private fun searchThroughDatabase(query: String) {
        val searchQuery = "%$query%"
        mListingViewModel.searchDatabase(searchQuery).observeOnce(viewLifecycleOwner, { list ->
            list?.let {
                adapter.differ.submitList(it)
            }
        })
    }

    //Item Selection Listener for the Action Bar Options
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete_all -> {
                //Delete All
                confirmDeleteAll()
            }
            R.id.menu_priority_high -> {
                //Observe Task With High Priority
                mListingViewModel.highPriorityData.observe(viewLifecycleOwner, { adapter.differ.submitList(it) })
            }
            R.id.menu_priority_low -> {
                //Observe Task With Low Priority
                mListingViewModel.lowPriorityData.observe(viewLifecycleOwner, { adapter.differ.submitList(it) })
            }
        }
        return super.onOptionsItemSelected(item)
    }

    //Showing SnackBar to Facilitate Restoring the Deleted Item
    private fun restoreDeletedItem(view: View, task: TaskModel) {
        Snackbar.make(view, "Deleted '${task.title}'", Snackbar.LENGTH_LONG)
            .setAction("UNDO") { mListingViewModel.insertData(task) }
            .show()
    }

    private fun confirmDeleteAll() {
        MaterialAlertDialogBuilder(requireContext())
            .setPositiveButton("Yes") { _, _ ->
                //Deleting All the Tasks in Database
                mListingViewModel.deleteAll()
                Toast.makeText(
                    requireContext(),
                    "Successfully Removed Everything",
                    Toast.LENGTH_LONG
                ).show()
            }
            .setNegativeButton("No") { _, _ -> }
            .setTitle("Delete Everything?")
            .setMessage("Are you sure you want to remove all?")
            .create().show()
    }

    override fun onDestroy() {
        super.onDestroy()
        //Clearing reference to the view
        _binding = null
    }
}