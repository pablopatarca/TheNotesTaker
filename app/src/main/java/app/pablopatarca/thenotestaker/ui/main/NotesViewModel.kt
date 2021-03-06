package app.pablopatarca.thenotestaker.ui.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.pablopatarca.thenotestaker.domain.Note
import app.pablopatarca.thenotestaker.domain.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val notesRepository: NotesRepository
) : ViewModel() {

    private val _state = mutableStateOf(NotesUIState())
    val state: State<NotesUIState> = _state

    private val deletedNote: Note? = null
    private var getNotesJob: Job? = null

    init {
        getNotes()
    }

    private fun getNotes(){
        getNotesJob?.cancel()

        getNotesJob = notesRepository.getNotesWithTags().onEach {
            _state.value = state.value.copy(
                notes = it
            )
        }.launchIn(viewModelScope)

        getNotesJob = notesRepository.getTags().onEach {
            _state.value = state.value.copy(
                tags = it
            )
        }.launchIn(viewModelScope)
    }
}