package app.pablopatarca.thenotestaker.data

import androidx.room.Entity

@Entity(primaryKeys = ["id", "noteId"])
data class NotebookNotesCrossRef(
    val id: Long,
    val noteId: Long
)
