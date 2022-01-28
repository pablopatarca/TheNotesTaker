package app.pablopatarca.thenotestaker.domain

import kotlinx.coroutines.flow.Flow

class TagsUseCase(
    private val repository: NotesRepository
) {

    operator fun invoke(): Flow<List<Tag>> {
        return repository.getTags()
    }
}