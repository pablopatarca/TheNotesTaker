package app.pablopatarca.thenotestaker.ui

sealed class Screen(val title: String, val icon: Int, val route: String) {
    object NotesScreen: Screen(
        "Notes",
        android.R.drawable.ic_menu_agenda,
        "notes_screen"
    )
    object EditScreen: Screen(
        "Edit Notes",
        0,
        "edit_note_screen"
    )
    object NotebooksScreen : Screen(
        "Notebooks",
        android.R.drawable.ic_menu_agenda,
        "notebooks_screen"
    )
    object TagsScreen : Screen(
        "Tags",
        android.R.drawable.ic_menu_info_details,
        "tags_screen"
    )
    object SearchScreen : Screen(
        "Search",
        android.R.drawable.ic_search_category_default,
        "search_screen"
    )
}
