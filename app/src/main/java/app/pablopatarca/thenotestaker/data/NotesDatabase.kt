package app.pablopatarca.thenotestaker.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [NoteDto::class, TagDto::class, NoteTagCrossRef::class],
    version = 1
)
abstract class NotesDatabase: RoomDatabase() {

    abstract val notesDao: NotesDao

    companion object {
        const val DATABASE_NAME = "THE_NOTES_TAKER"
    }
}