package app.pablopatarca.thenotestaker.domain

sealed class NotesFilter(val id: Long) {
    class Tag(id: Long) : NotesFilter(id)
    class Color(id: Long) : NotesFilter(id)
}
