package app.pablopatarca.thenotestaker.ui.edit

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.pablopatarca.thenotestaker.data.Note
import app.pablopatarca.thenotestaker.data.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditNoteViewModel @Inject constructor(
    private val notesRepository: NotesRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _noteTitle = mutableStateOf("")
    val noteTitle: State<String> = _noteTitle
    private val _noteContent = mutableStateOf("")
    val noteContent: State<String> = _noteContent

    private var currentNoteId: Int? = null

    init {
        savedStateHandle.get<Int>("id")?.let { noteId ->
            if(noteId != -1) {
                viewModelScope.launch {
                    notesRepository.getNoteById(noteId)?.also { note ->
                        currentNoteId = note.id
                        _noteTitle.value = note.title
                        _noteContent.value = note.content
                    }
                }
            }
        }
    }

    fun enteredTitle(title: String){
        _noteTitle.value = title
    }

    fun enteredContent(content: String){
        _noteContent.value = content
    }

    fun saveNote(){
        viewModelScope.launch {
            try {

                _noteTitle.value = noteTitle.value
                _noteContent.value = noteContent.value

                notesRepository.insert(
                    Note(
                        id = currentNoteId,
                        title = noteTitle.value,
                        content = noteContent.value,
                        createdAt = System.currentTimeMillis(),
                        updatedAt = System.currentTimeMillis(),
                        color = 0
                    )
                )
            } catch(e: Exception) {
                e.printStackTrace()
            }
        }
    }

}