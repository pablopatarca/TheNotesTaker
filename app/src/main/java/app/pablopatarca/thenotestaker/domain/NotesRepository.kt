package app.pablopatarca.thenotestaker.domain

import kotlinx.coroutines.flow.Flow

interface NotesRepository {

    fun getNotes(): Flow<List<Note>>

    suspend fun getNoteById(id: Int): Note?

    suspend fun insert(note: Note)

    suspend fun delete(note: Note)

    fun getNotesWithTags(): Flow<List<Note>>

    fun getNotesByTag(tag: String): Flow<List<Note>>

    suspend fun insert(tag: Tag): Long

    fun getTags(): Flow<List<Tag>>
}