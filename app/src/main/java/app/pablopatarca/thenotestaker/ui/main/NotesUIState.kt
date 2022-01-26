package app.pablopatarca.thenotestaker.ui.main

import app.pablopatarca.thenotestaker.domain.Note
import app.pablopatarca.thenotestaker.domain.Notebook
import app.pablopatarca.thenotestaker.domain.Tag

data class NotesUIState(
    val notebooks: List<Notebook> = listOf(),
    val notes: List<Note> = listOf(),
    val tags: List<Tag> = listOf()
)
