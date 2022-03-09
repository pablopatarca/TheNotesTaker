package app.pablopatarca.thenotestaker.ui.edit

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.pablopatarca.thenotestaker.domain.Note
import app.pablopatarca.thenotestaker.domain.NotesUseCase
import app.pablopatarca.thenotestaker.domain.Tag
import app.pablopatarca.thenotestaker.domain.TagsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditNoteViewModel @Inject constructor(
    private val notesUseCase: NotesUseCase,
    private val tagsUseCase: TagsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _noteTitle = mutableStateOf("")
    val noteTitle: State<String> = _noteTitle
    private val _noteContent = mutableStateOf("")
    val noteContent: State<String> = _noteContent
    private val _noteTags = mutableStateOf("")
    val noteTags: State<String> = _noteTags
    private val _noteColor = mutableStateOf(Note.noteColors.first().toArgb())
    val noteColor: State<Int> = _noteColor

    private val _tagsList: MutableState<List<Tag>> = mutableStateOf(listOf())
    val tagsList: State<List<Tag>> = _tagsList

    private var currentNoteId: Long? = null
    private var createdAt: Long? = null

    init {
        viewModelScope.launch {

            tagsUseCase().collectLatest {
                _tagsList.value = it
            }

            savedStateHandle.get<Long>("id")?.let { noteId ->
                if(noteId != -1L) {
                    notesUseCase(noteId)?.also { note ->
                        currentNoteId = note.id
                        _noteTitle.value = note.title
                        _noteContent.value = note.content
                        createdAt = note.createdAt
                        _noteTags.value = note.tags.toStringTags()
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

    fun enteredTags(tags: String){
        _noteTags.value = tags
    }

    fun setColor(color: Int){
        _noteColor.value = color
    }

    fun saveNote(){
        viewModelScope.launch {
            try {
                _noteTitle.value = noteTitle.value
                _noteContent.value = noteContent.value
                _noteTags.value = noteTags.value
                val tagsList = noteTags.value.toTagList()

                val currentTime = System.currentTimeMillis()

                notesUseCase.insert(
                    Note(
                        id = currentNoteId,
                        title = noteTitle.value,
                        content = noteContent.value,
                        createdAt = createdAt ?: currentTime,
                        updatedAt = currentTime,
                        color = noteColor.value,
                        tags = tagsList
                    )
                )
            } catch(e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun List<Tag>.toStringTags(): String {
        return joinToString(", "){ it.name }
    }

    private fun String.toTagList(): List<Tag> {
        return split(",")
            .map { str ->
            val tagName = str.replace(" ","")
            _tagsList.value.firstOrNull {
                it.name.equals(tagName, true)
            } ?: Tag(name = tagName)
        }
    }
}