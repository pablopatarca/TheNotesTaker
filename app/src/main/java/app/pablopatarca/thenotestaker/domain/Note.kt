package app.pablopatarca.thenotestaker.domain

import app.pablopatarca.thenotestaker.ui.theme.*

data class Note(
    val id: Long? = null,
    val title: String,
    val content: String,
    val createdAt: Long,
    val updatedAt: Long,
    val color: Int,
    val tags: List<Tag> = listOf()
){
    companion object {
        val noteColors = listOf(NoteWhite, NoteRed, NoteLime, NoteBlue, NoteGreen)
    }
}
