package app.pablopatarca.thenotestaker.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    @Query("SELECT * FROM NoteDto")
    fun getNotes(): Flow<List<NoteDto>>

    @Query("SELECT * FROM NoteDto where noteId = :id")
    suspend fun getNoteById(id: Long): NoteWithTags?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: NoteDto): Long

    @Delete
    suspend fun delete(note: NoteDto)

    @Transaction
    @Query("SELECT * FROM NoteDto")
    fun getNotesTags(): Flow<List<NoteWithTags>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(tag: TagDto): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(noteTagRef: NoteTagCrossRef)

    @Query("DELETE FROM NoteTagCrossRef WHERE noteId = :noteId")
    suspend fun cleanRefTags(noteId: Long)

    @Query("SELECT * FROM TagDto")
    fun getTags(): Flow<List<TagDto>>
}