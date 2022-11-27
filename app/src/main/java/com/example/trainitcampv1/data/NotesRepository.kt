package com.example.trainitcampv1.data

import androidx.lifecycle.LiveData
import androidx.room.Query
import com.example.trainitcampv1.data.entity.Notes
import com.example.trainitcampv1.data.room.NotesDao

class NotesRepository(private val noteDao: NotesDao) {

    val getAllNotes: LiveData<List<Notes>> = noteDao.getAllNotes()
    val sortByHighPriority: LiveData<List<Notes>> = noteDao.sortByHighPriority()
    val sortByLowPriority: LiveData<List<Notes>> = noteDao.sortByLowPriority()

    suspend fun insertNotes(notes: Notes) {
        noteDao.insertNotes(notes)
    }

    fun searchNoteByQuery(searchQuery: String): LiveData<List<Notes>> {
        return noteDao.searchNoteByQuery(searchQuery)
    }

    suspend fun deleteAll() {
        noteDao.deleteAll()
    }

    suspend fun deleteNotes(notes: Notes) {
        noteDao.deleteNotes(notes)
    }

    suspend fun updateNotes(notes: Notes) {
        noteDao.updateNotes(notes)
    }
}