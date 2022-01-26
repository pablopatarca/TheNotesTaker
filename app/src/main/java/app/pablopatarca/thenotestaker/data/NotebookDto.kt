package app.pablopatarca.thenotestaker.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NotebookDto(
    @PrimaryKey val id: Long? = null,
    val name: String
)