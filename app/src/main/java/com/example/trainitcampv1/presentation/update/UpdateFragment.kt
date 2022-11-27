package com.example.trainitcampv1.presentation.update

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.trainitcampv1.R
import com.example.trainitcampv1.data.entity.Notes
import com.example.trainitcampv1.databinding.FragmentUpdateBinding
import com.example.trainitcampv1.presentation.NotesViewModel
import com.example.trainitcampv1.utils.ExtensionFunction.setupActionBar
import com.example.trainitcampv1.utils.HelperFunctions.dateTodaySimpleFormat
import com.example.trainitcampv1.utils.HelperFunctions.parseToPriority
import com.example.trainitcampv1.utils.HelperFunctions.spinnerListener
import com.example.trainitcampv1.utils.HelperFunctions.verifyDataFromUser

class UpdateFragment : Fragment() {

    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding as FragmentUpdateBinding

    private val updateViewModel by viewModels<NotesViewModel>()
    private val navArgs by navArgs<UpdateFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.safeArgs = navArgs

        setHasOptionsMenu(true)

        binding.apply {
            toolbarUpdate.setupActionBar(this@UpdateFragment, R.id.updateFragment)
            spinnerPrioritiesUpdate.onItemSelectedListener =
                spinnerListener(context, binding.priorityIndicator)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.id.menu_save, menu)

        val item = menu.findItem(R.id.menu_save)
        item.actionView.findViewById<AppCompatImageButton>(R.id.btn_save).setOnClickListener {
            updateItem()
        }
    }

    private fun updateItem() {
        binding.apply {
            val title = editTitleUpdate.text.toString()
            val desc = editDescriptionUpdate.text.toString()
            val priority = spinnerPrioritiesUpdate.selectedItem.toString()

            val validation = verifyDataFromUser(title, desc)
            if (validation) {
                val updateItem = Notes(
                    navArgs.currentItem.id,
                    title,
                    parseToPriority(context, priority),
                    desc,
                    getString(R.string.txt_edited_on) + "\n" + dateTodaySimpleFormat()
                )
                updateViewModel.updateData(updateItem)
                Toast.makeText(context, "Successfully update.", Toast.LENGTH_SHORT)
                    .show()

                // karena disii bawa data update,
                val action =
                    UpdateFragmentDirections.actionUpdateFragmentToDetailFragment(updateItem)
                findNavController().navigate(action)
            } else {
                Toast.makeText(context, "Please input title and description", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}