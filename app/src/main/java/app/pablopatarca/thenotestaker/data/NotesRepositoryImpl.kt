package app.pablopatarca.thenotestaker.data

import app.pablopatarca.thenotestaker.domain.Note
import app.pablopatarca.thenotestaker.domain.NotesRepository
import app.pablopatarca.thenotestaker.domain.Tag
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

    override suspend fun getNoteById(id: Long): Note? {
        return dataSource.getNoteById(id)?.toNote()
    }

    override suspend fun insert(note: Note) {

        val noteId = dataSource.insert(note.toNoteDto())

        dataSource.cleanRefTags(noteId)

        note.tags.forEach {

            val tagId = insert(it)

            val noteTagRef = NoteTagCrossRef(
                noteId = noteId,
                tagId = tagId
            )

            dataSource.insert(noteTagRef)
        }
    }

    override suspend fun delete(note: Note) {
        dataSource.delete(note.toNoteDto())
    }

    override fun getNotesWithTags(): Flow<List<Note>> {
        return dataSource.getNotesTags().map { list ->
            list.map {
                it.toNote()
            }
        }
    }

    override fun getNotesByTag(tag: String): Flow<List<Note>> {
        TODO("Not yet implemented")
    }

    override suspend fun insert(tag: Tag): Long {
        return dataSource.insert(TagDto(name = tag.name))
    }

    override fun getTags(): Flow<List<Tag>> {
        return dataSource.getTags().map { list ->
            list.map {
                Tag(it.tagId, it.name)
            }
        }
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