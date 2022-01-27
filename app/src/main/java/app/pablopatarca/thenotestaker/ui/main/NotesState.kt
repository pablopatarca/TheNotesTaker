package app.pablopatarca.thenotestaker.ui.main

import app.pablopatarca.thenotestaker.domain.Note
import app.pablopatarca.thenotestaker.domain.NotesFilter
import app.pablopatarca.thenotestaker.domain.Tag

data class NotesState(
    val notes: List<Note> = listOf(),
    val tags: List<Tag> = listOf(),

    val filter: NotesFilter? = null
)
