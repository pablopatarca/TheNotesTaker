package app.pablopatarca.thenotestaker.data

data class Note(
    val id: Int? = null,
    val title: String,
    val content: String,
    val createdAt: Long,
    val updatedAt: Long,
    val color: Int,
    val tags: List<String>
)
