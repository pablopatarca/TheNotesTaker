package app.pablopatarca.thenotestaker.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NoteDto(
    @PrimaryKey val noteId: Long? = null,
    val title: String,
    val content: String,
    val createdAt: Long,
    val updatedAt: Long,
    val color: Int
)
