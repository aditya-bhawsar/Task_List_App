package com.aditya.to_do.ui.fragments

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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.aditya.to_do.R
import com.aditya.to_do.databinding.FragmentListingBinding
import com.aditya.to_do.model.TaskModel
import com.aditya.to_do.ui.activity.AppActivity
import com.aditya.to_do.ui.adapters.TaskAdapter
import com.aditya.to_do.util.Utils.observeOnce
import com.aditya.to_do.viewModel.ListingViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListingFragment : Fragment(R.layout.fragment_listing) {

    private val mListingViewModel: ListingViewModel by viewModels()

    private val adapter: TaskAdapter by lazy { TaskAdapter() }

    private var _binding: FragmentListingBinding? = null
    private val binding
        get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentListingBinding.bind(view)

        binding.apply {
            lifecycleOwner = this@ListingFragment
            viewModel = mListingViewModel
            listRv.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            listRv.adapter = adapter

            swipeToDelete(listRv)
        }

        mListingViewModel.getAllData.observe(viewLifecycleOwner, { data ->
            mListingViewModel.checkIfDatabaseEmpty(data.isEmpty())
            adapter.differ.submitList(data)
            binding.listRv.scheduleLayoutAnimation()
        })

        setHasOptionsMenu(true)
        (activity as AppActivity).supportActionBar!!.setDisplayShowHomeEnabled(true)
        (activity as AppActivity).supportActionBar!!.setIcon(R.drawable.ic_to_do)
    }

    private fun swipeToDelete(listRv: RecyclerView) {
        val swipeToDelete = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val itemToDelete = adapter.differ.currentList[viewHolder.adapterPosition]
                mListingViewModel.deleteData(itemToDelete)
                adapter.notifyItemRemoved(viewHolder.adapterPosition)
                restoreDeletedItem(listRv, itemToDelete)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDelete)
        itemTouchHelper.attachToRecyclerView(listRv)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    searchThroughDatabase(query)
                }
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query != null) {
                    searchThroughDatabase(query)
                }
                return true
            }
        })
    }

    private fun searchThroughDatabase(query: String) {
        val searchQuery = "%$query%"
        println(searchQuery)
        mListingViewModel.searchDatabase(searchQuery).observeOnce(viewLifecycleOwner, { list ->
            list?.let {
                adapter.differ.submitList(it)
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete_all -> {
                confirmDeleteAll()
            }
            R.id.menu_priority_high -> {
                mListingViewModel.highPriorityData.observe(
                    viewLifecycleOwner,
                    { adapter.differ.submitList(it) })
            }
            R.id.menu_priority_low -> {
                mListingViewModel.lowPriorityData.observe(
                    viewLifecycleOwner,
                    { adapter.differ.submitList(it) })
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun restoreDeletedItem(view: View, task: TaskModel) {
        Snackbar.make(view, "Deleted '${task.title}'", Snackbar.LENGTH_LONG)
            .setAction("UNDO") { mListingViewModel.insertData(task) }
            .show()
    }

    private fun confirmDeleteAll() {
        MaterialAlertDialogBuilder(requireContext())
            .setPositiveButton("Yes") { _, _ ->
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
        _binding = null
    }
}