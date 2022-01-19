package app.pablopatarca.thenotestaker.data

import app.pablopatarca.thenotestaker.domain.Note
import app.pablopatarca.thenotestaker.domain.NotesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NotesRepositoryImpl(
    private val dataSource: NotesDao
): NotesRepository {

    override fun getNotes(): Flow<List<Note>> {
        return dataSource.getNotes().map { notesList ->
            notesList.map {
                it.toNote()
            }
        }
    }

    override suspend fun getNoteById(id: Int): Note? {
        return dataSource.getNoteById(id)?.toNote()
    }

    override suspend fun insert(note: Note) {
        return dataSource.insert(note.toNoteDto())
    }

    override suspend fun delete(note: Note) {
        return dataSource.delete(note.toNoteDto())
    }

    private fun Note.toNoteDto(): NoteDto {
        return NoteDto(
            id = id,
            title = title,
            content = content,
            createdAt = createdAt,
            updatedAt = updatedAt,
            color = color
        )
    }

    private fun NoteDto.toNote(): Note {
        return Note(
            id = id,
            title = title,
            content = content,
            createdAt = createdAt,
            updatedAt = updatedAt,
            color = color
        )
    }

}