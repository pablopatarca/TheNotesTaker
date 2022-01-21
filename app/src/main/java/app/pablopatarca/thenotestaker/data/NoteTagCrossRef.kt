package app.pablopatarca.thenotestaker.data

import androidx.room.Entity

@Entity(primaryKeys = ["noteId", "tagId"])
data class NoteTagCrossRef(
    val noteId: Long,
    val tagId: Long,
)
