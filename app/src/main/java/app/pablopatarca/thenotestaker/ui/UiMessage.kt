package app.pablopatarca.thenotestaker.ui

data class UiMessage(
    val message: String,
    val undo: Boolean = false,
)
