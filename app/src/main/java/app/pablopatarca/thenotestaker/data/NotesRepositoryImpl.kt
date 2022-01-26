package app.pablopatarca.thenotestaker.data

import app.pablopatarca.thenotestaker.domain.Note
import app.pablopatarca.thenotestaker.domain.Notebook
import app.pablopatarca.thenotestaker.domain.NotesRepository
import app.pablopatarca.thenotestaker.domain.Tag
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NotesRepositoryImpl(
    private val notebooksDS: NotebookDao,
    private val notesDS: NotesDao
): NotesRepository {

    override fun getNotebooks(): Flow<List<Notebook>> {
        return notebooksDS.getNotebooks().map { notesList ->
            notesList.map {
                it.toNotebook()
            }
        }
    }

    override fun getNotes(): Flow<List<Note>> {
        return notesDS.getNotes().map { notesList ->
            notesList.map {
                it.toNote()
            }
        }
    }

    override suspend fun getNoteById(id: Int): Note? {
        return notesDS.getNoteById(id)?.toNote()
    }

    override suspend fun insert(note: Note) {

        val noteId = notesDS.insert(note.toNoteDto())

        note.tags.forEach {

            val tagId = insert(it)

            val noteTagRef = NoteTagCrossRef(
                noteId = noteId,
                tagId = tagId
            )

            notesDS.insert(noteTagRef)
        }
    }

    override suspend fun delete(note: Note) {
        notesDS.delete(note.toNoteDto())
    }

    override fun getNotesWithTags(): Flow<List<Note>> {
        return notesDS.getNotesTags().map { list ->
            list.map {
                it.toNote()
            }
        }
    }

    override fun getNotesByTag(tag: String): Flow<List<Note>> {
        TODO("Not yet implemented")
    }

    override suspend fun insert(tag: Tag): Long {
        return notesDS.insert(TagDto(name = tag.name))
    }

    override fun getTags(): Flow<List<Tag>> {
        return notesDS.getTags().map { list ->
            list.map {
                Tag(it.tagId, it.name)
            }
        }
    }


    private fun NotebookDto.toNotebook(): Notebook {
        return Notebook(
            id = id,
            name = name
        )
    }

    private fun Note.toNoteDto(): NoteDto {
        return NoteDto(
            noteId = id,
            title = title,
            content = content,
            createdAt = createdAt,
            updatedAt = updatedAt,
            color = color
        )
    }
    private fun Note.toNoteWithTags(): NoteWithTags {
        return NoteWithTags(
            note = toNoteDto(),
            tags = tags.map {
                TagDto(name = it.name)
            }
        )
    }

    private fun NoteDto.toNote(): Note {
        return Note(
            id = noteId,
            title = title,
            content = content,
            createdAt = createdAt,
            updatedAt = updatedAt,
            color = color,
            tags = listOf()
        )
    }

    private fun NoteWithTags.toNote(): Note {
        return Note(
            id = note.noteId,
            title = note.title,
            content = note.content,
            createdAt = note.createdAt,
            updatedAt = note.updatedAt,
            color = note.color,
            tags = tags.map {
                Tag(
                    id = it.tagId,
                    name = it.name
                )
            }
        )
    }

}