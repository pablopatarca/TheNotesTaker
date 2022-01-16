package app.pablopatarca.thenotestaker.ui.main

import app.pablopatarca.thenotestaker.data.Note

data class NotesUIState(
    val notes: List<Note> = listOf()
)
