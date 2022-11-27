package com.example.trainitcampv1.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Query
import com.example.trainitcampv1.data.NotesRepository
import com.example.trainitcampv1.data.entity.Notes
import com.example.trainitcampv1.data.room.NotesDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModel(application: Application) : AndroidViewModel(application) {
    private val notesDao = NotesDatabase.getDatabase(application).notesDao()
    private val repository: NotesRepository = NotesRepository(notesDao)

    fun insertData(notes: Notes) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertNotes(notes)
        }
    }

    fun getAllData(): LiveData<List<Notes>> = repository.getAllNotes

    fun searchNoteByQuery(searchQuery: String): LiveData<List<Notes>> {
        return repository.searchNoteByQuery(searchQuery)
    }

    fun sortByHighPriority(): LiveData<List<Notes>> = repository.sortByHighPriority
    fun sortByLowPriority(): LiveData<List<Notes>> = repository.sortByLowPriority

    // Delete All
    fun deleteAllData() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }

    // Delete Data
    fun deleteData(notes: Notes) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteNotes(notes)
        }
    }

    // Update Data
    fun updateData(notes: Notes) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateNotes(notes)
        }
    }
}