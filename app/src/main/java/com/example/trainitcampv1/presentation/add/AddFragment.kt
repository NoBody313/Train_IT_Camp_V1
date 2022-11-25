package com.example.trainitcampv1.presentation.add

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.trainitcampv1.R
import com.example.trainitcampv1.data.entity.Notes
import com.example.trainitcampv1.databinding.FragmentAddBinding
import com.example.trainitcampv1.presentation.NotesViewModel
import com.example.trainitcampv1.utils.ExtensionFunction.setupActionBar
import com.example.trainitcampv1.utils.HelperFunctions.dateTodaySimpleFormat
import com.example.trainitcampv1.utils.HelperFunctions.parseToPriority
import com.example.trainitcampv1.utils.HelperFunctions.verifyDataFromUser

class AddFragment : Fragment() {

    private var _binding:FragmentAddBinding? = null
    private val binding get() = _binding as FragmentAddBinding
    private val addViewModel by viewModels<NotesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        binding.apply {
            toolbarAdd.setupActionBar(this@AddFragment, R.id.addFragment)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_save, menu)
        val item = menu.findItem(R.id.menu_save)
        item.actionView.findViewById<AppCompatImageButton>(R.id.btn_save).setOnClickListener {
            insertNotes()
        }
    }

    private fun insertNotes() {
        binding.apply {
            val title = editTitle.text.toString()
            val priority = spinnerPriorities.selectedItem.toString()
            val description = editDescription.text.toString()

            val validation = verifyDataFromUser(title, description)
            if (validation) {
                val note = Notes(
                    0,
                    title,
                    parseToPriority(context, priority),
                    description,
                    dateTodaySimpleFormat()
                )
                addViewModel.insertData(note)
                Toast.makeText(context, "Successfully added.", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_addFragment_to_homeFragment)
            } else {
                Toast.makeText(context, "Please input title and description.", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}