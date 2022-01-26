package app.pablopatarca.thenotestaker.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface NotebookDao {

    @Query("SELECT * FROM NotebookDto")
    fun getNotebooks(): Flow<List<NotebookDto>>

    @Query("SELECT * FROM NotebookDto where id = :id")
    suspend fun getNoteById(id: Int): NotebookDto?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(tag: NotebookDto): Long

    @Delete
    suspend fun delete(note: NotebookDto)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(noteTagRef: NotebookNotesCrossRef)
}