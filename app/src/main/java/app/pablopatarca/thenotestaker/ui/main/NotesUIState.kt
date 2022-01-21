package app.pablopatarca.thenotestaker.ui.main

import app.pablopatarca.thenotestaker.domain.Note
import app.pablopatarca.thenotestaker.domain.Tag

data class NotesUIState(
    val notes: List<Note> = listOf(),
    val tags: List<Tag> = listOf()
)
