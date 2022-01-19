package app.pablopatarca.thenotestaker.ui.edit

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.pablopatarca.thenotestaker.domain.Note
import app.pablopatarca.thenotestaker.domain.NotesRepository
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
    private var createdAt: Long? = null

    init {
        savedStateHandle.get<Int>("id")?.let { noteId ->
            if(noteId != -1) {
                viewModelScope.launch {
                    notesRepository.getNoteById(noteId)?.also { note ->
                        currentNoteId = note.id
                        _noteTitle.value = note.title
                        _noteContent.value = note.content
                        createdAt = note.createdAt
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
                val currentTime = System.currentTimeMillis()

                notesRepository.insert(
                    Note(
                        id = currentNoteId,
                        title = noteTitle.value,
                        content = noteContent.value,
                        createdAt = createdAt ?: currentTime,
                        updatedAt = currentTime,
                        color = 0
                    )
                )
            } catch(e: Exception) {
                e.printStackTrace()
            }
        }
    }

}