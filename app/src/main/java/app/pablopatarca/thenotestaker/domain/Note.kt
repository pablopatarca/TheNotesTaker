package app.pablopatarca.thenotestaker.domain

data class Note(
    val id: Int? = null,
    val title: String,
    val content: String,
    val createdAt: Long,
    val updatedAt: Long,
    val color: Int
)
