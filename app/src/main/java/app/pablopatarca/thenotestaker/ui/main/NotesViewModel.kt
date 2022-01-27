package app.pablopatarca.thenotestaker.ui.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.pablopatarca.thenotestaker.domain.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val notesUseCase: NotesUseCase,
    private val tagsUseCase: TagsUseCase
) : ViewModel() {

    private val _state = mutableStateOf(NotesState())
    val state: State<NotesState> = _state

    private val deletedNote: Note? = null
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

    private fun getNotes(filter: NotesFilter? = null){
        getNotesJob?.cancel()

//        val filter = state.value.filter?.id?.let {
//            NotesFilter.Tag(it)
//        }

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
}