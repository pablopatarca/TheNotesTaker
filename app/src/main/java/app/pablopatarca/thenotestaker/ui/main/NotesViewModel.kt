package app.pablopatarca.thenotestaker.ui.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.pablopatarca.thenotestaker.domain.*
import app.pablopatarca.thenotestaker.ui.UiMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val notesUseCase: NotesUseCase,
    private val tagsUseCase: TagsUseCase
) : ViewModel() {

    private val _state = mutableStateOf(NotesState())
    val state: State<NotesState> = _state

    // To emit an state once
    // Examples: Navigation, Snackbar messages, etc
    private val _messages = MutableSharedFlow<UiMessage>()
    val messages = _messages.asSharedFlow()

    private var deletedNote: Note? = null
    private var getNotesJob: Job? = null

    init {
        getNotes()
    }

    fun onEvent(event: NotesEvent){

        when(event){
            is NotesEvent.Filter -> {
//                _state.value = state.value.copy(
//                    filter = event.filter
//                )
                getNotes(event.filter)
            }
        }
    }

    fun more(){
        viewModelScope.launch {
            _messages.emit(UiMessage("More selected"))
        }
    }

    private fun getNotes(filter: NotesFilter? = null){
        getNotesJob?.cancel()

        getNotesJob = notesUseCase(filter).onEach {
            _state.value = state.value.copy(
                notes = it
            )
        }.launchIn(viewModelScope)

        getNotesJob = tagsUseCase().onEach {
            _state.value = state.value.copy(
                tags = it
            )
        }.launchIn(viewModelScope)
    }

    fun deleteNote(id: Long) {
        viewModelScope.launch {
            notesUseCase.invoke(id)?.let {
                deletedNote = it
                notesUseCase.delete(it)
                _messages.emit(
                    UiMessage("Note deleted", true)
                )
            } ?: _messages.emit(UiMessage("Note not found"))
        }
    }

    fun undoDelete() {
        viewModelScope.launch {
            deletedNote?.let {
                notesUseCase.insert(it)
                deletedNote = null
            } ?: _messages.emit(UiMessage(""))
        }
    }
}