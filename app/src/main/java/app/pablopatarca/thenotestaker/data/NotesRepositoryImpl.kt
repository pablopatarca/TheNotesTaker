package app.pablopatarca.thenotestaker.data

import kotlinx.coroutines.flow.Flow

class NotesRepositoryImpl(
    private val dataSource: NotesDao
): NotesRepository {

    override fun getNotes(): Flow<List<Note>> {
        return dataSource.getNotes()
    }

    override suspend fun getNoteById(id: Int): Note? {
        return dataSource.getNoteById(id)
    }

    override suspend fun insert(note: Note) {
        return dataSource.insert(note)
    }

    override suspend fun delete(note: Note) {
        return dataSource.delete(note)
    }


}