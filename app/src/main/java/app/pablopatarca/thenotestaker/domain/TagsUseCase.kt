package app.pablopatarca.thenotestaker.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TagsUseCase(
    private val repository: NotesRepository
) {

    operator fun invoke(): Flow<List<Tag>> {
        return repository.getTags()
    }
}