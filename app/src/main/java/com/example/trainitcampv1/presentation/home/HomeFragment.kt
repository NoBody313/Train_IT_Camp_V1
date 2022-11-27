package com.example.trainitcampv1.presentation.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.trainitcampv1.R
import com.example.trainitcampv1.data.entity.Notes
import com.example.trainitcampv1.databinding.FragmentHomeBinding
import com.example.trainitcampv1.presentation.NotesViewModel
import com.example.trainitcampv1.utils.ExtensionFunction.observeOnce
import com.example.trainitcampv1.utils.ExtensionFunction.setupActionBar
import com.example.trainitcampv1.utils.HelperFunctions
import com.example.trainitcampv1.utils.HelperFunctions.checkIfDatabaseEmpty
import com.google.android.material.snackbar.Snackbar

class HomeFragment : androidx.fragment.app.Fragment(), SearchView.OnQueryTextListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding as FragmentHomeBinding

    private val homeAdapter by lazy { HomeAdapter() }
    private val homeViewModel by viewModels<NotesViewModel>()

    private var currentData = emptyList<Notes>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.mHelperFunction = HelperFunctions

        setHasOptionsMenu(true)
        binding.toolbarHome.setupActionBar(this, null)
        setupRecycleView()
    }

    private fun setupRecycleView() {
        binding.rvNotes.apply {
            homeViewModel.getAllData().observe(viewLifecycleOwner) {
                checkIfDatabaseEmpty(it)
                homeAdapter.setData(it)
                scheduleLayoutAnimation()
                currentData = it
            }
            adapter = homeAdapter
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

            swipeToDelete(this)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_home, menu)

        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as? android.widget.SearchView
        searchView?.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchNote(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) {
            searchNote(newText)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete_all -> deleteAllData()
            R.id.menu_priority_high -> homeViewModel.sortByHighPriority().observe(this) {
                homeAdapter.setData(it)
            }
            R.id.menu_priority_low -> homeViewModel.sortByLowPriority().observe(this) {
                homeAdapter.setData(it)
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onDestroy() {
        super.onDestroyView()
        _binding = null
    }

    // Private Fun Section
    private fun searchNote(query: String) {
        val searchQuery = "%$query%"

        homeViewModel.searchNoteByQuery(searchQuery).observeOnce(this) {
            homeAdapter.setData(it)
        }
    }

    private fun deleteAllData() {
        if (currentData.isEmpty()) {
            AlertDialog.Builder(requireContext()).setTitle("No Data Found.")
                .setMessage("No data found for deletes.")
                .setPositiveButton("Ok") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        } else {
            AlertDialog.Builder(requireContext())
                .setTitle("Delete everything?")
                .setMessage("Are you sure to remove everything?")
                .setPositiveButton("Yes") { _, _ ->
                    homeViewModel.deleteAllData()
                    Toast.makeText(
                        requireContext(),
                        "Successfully Removed Everything",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                .setNegativeButton("No", null)
                .show()
        }
    }

    private fun swipeToDelete(recyclerView: RecyclerView) {
        val swipeToDeleteCallback = object : SwipeToDelete() {

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deleteItem = homeAdapter.listNotes[viewHolder.adapterPosition]
                // Delete Item
                homeViewModel.deleteData(deleteItem)
                homeAdapter.notifyItemRemoved(viewHolder.adapterPosition)

                // Restore Delete Item
                restoreDeletedData(viewHolder.itemView, deleteItem)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun restoreDeletedData(view: View, deleteItem: Notes) {
        Snackbar.make(view, "Delete: '${deleteItem.title}'", Snackbar.LENGTH_LONG)
            .setTextColor(ContextCompat.getColor(view.context, R.color.black))
            .setAction("Undo") {
                homeViewModel.insertData(deleteItem)
            }
            .setActionTextColor(ContextCompat.getColor(view.context, R.color.black))
            .show()
    }
}