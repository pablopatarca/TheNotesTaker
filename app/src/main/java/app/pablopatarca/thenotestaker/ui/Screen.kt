package app.pablopatarca.thenotestaker.ui

sealed class Screen(val route: String) {
    object NoteScreen: Screen("note_screen")
    object EditScreen: Screen("edit_note_screen")
}
