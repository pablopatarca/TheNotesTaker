package app.pablopatarca.thenotestaker.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TagDto(
    @PrimaryKey val tagId: Long? = null,
    val name: String
)