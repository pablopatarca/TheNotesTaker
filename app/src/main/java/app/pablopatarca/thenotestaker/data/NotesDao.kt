package app.pablopatarca.thenotestaker.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface NotesDao {

    @Query("SELECT * FROM NoteDto")
    fun getNotes(): Flow<List<NoteDto>>

    @Query("SELECT * FROM NoteDto where id = :id")
    suspend fun getNoteById(id: Int): NoteDto?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: NoteDto)

    @Delete
    suspend fun delete(note: NoteDto)
}