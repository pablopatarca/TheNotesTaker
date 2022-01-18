package app.pablopatarca.thenotestaker.data

import android.graphics.Color
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeNotesDao(

): NotesDao {
    override fun getNotes(): Flow<List<Note>> {
        val notes = listOf(
            Note(
                id = 1,
                title = "First Note",
                content = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis(),
                color = Color.YELLOW
            ),
            Note(
                id = 2,
                title = "Second Note",
                content = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis(),
                color = Color.YELLOW
            ),
            Note(
                id = 3,
                title = "Third Note",
                content = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis(),
                color = Color.YELLOW
            ),
            Note(
                id = 4,
                title = "Forth Note",
                content = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis(),
                color = Color.YELLOW
            )
        )
        return flow {
            emit(notes)
        }
    }

    override suspend fun getNoteById(id: Int): Note? {
        TODO("Not yet implemented")
    }

    override suspend fun insert(note: Note) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(note: Note) {
        TODO("Not yet implemented")
    }
}