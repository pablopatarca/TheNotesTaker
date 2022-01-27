package app.pablopatarca.thenotestaker.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NotesUseCase(
    private val repository: NotesRepository
) {

    operator fun invoke(
        filter: NotesFilter? = null
    ): Flow<List<Note>> {
        return repository.getNotesWithTags().map { notes ->
            when(filter){
                is NotesFilter.Tag -> {
                    notes.filter { note ->
                        note.tags.any { it.id == filter.id }
                    }
                }
                is NotesFilter.Color -> {
                    notes.filter { note ->
                        note.color == filter.id.toInt()
                    }
                }
                else -> notes
            }
        }
    }

    operator fun invoke(
        noteOrder: NoteOrder = NoteOrder.UpdatedAt(OrderType.Descending)
    ): Flow<List<Note>> {
        return repository.getNotes().map { notes ->
            when(noteOrder.orderType){
                is OrderType.Ascending -> {
                    when(noteOrder) {
                        is NoteOrder.UpdatedAt -> notes.sortedBy { it.updatedAt }
                        is NoteOrder.CreatedAt -> notes.sortedBy { it.createdAt }
                        is NoteOrder.Color -> notes.sortedBy { it.color }
                    }
                }
                is OrderType.Descending -> {
                    when(noteOrder) {
                        is NoteOrder.UpdatedAt -> notes.sortedByDescending { it.updatedAt }
                        is NoteOrder.CreatedAt -> notes.sortedByDescending { it.createdAt }
                        is NoteOrder.Color -> notes.sortedByDescending { it.color }
                    }
                }
            }
        }
    }
}