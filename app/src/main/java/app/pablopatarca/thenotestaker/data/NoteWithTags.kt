package app.pablopatarca.thenotestaker.data

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class NoteWithTags(
    @Embedded val note: NoteDto,
    @Relation(
        parentColumn = "noteId",
        entityColumn = "tagId",
        associateBy = Junction(NoteTagCrossRef::class)
    )
    val tags: List<TagDto>
)
