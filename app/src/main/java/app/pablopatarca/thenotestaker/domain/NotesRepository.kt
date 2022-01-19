package app.pablopatarca.thenotestaker.domain

import kotlinx.coroutines.flow.Flow

interface NotesRepository {

    fun getNotes(): Flow<List<Note>>

    suspend fun getNoteById(id: Int): Note?

    suspend fun insert(note: Note)

    suspend fun delete(note: Note)
}