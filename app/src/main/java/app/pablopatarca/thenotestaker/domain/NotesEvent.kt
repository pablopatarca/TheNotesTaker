package app.pablopatarca.thenotestaker.domain

sealed class NotesEvent {
    data class Filter(val filter: NotesFilter?): NotesEvent()
}
