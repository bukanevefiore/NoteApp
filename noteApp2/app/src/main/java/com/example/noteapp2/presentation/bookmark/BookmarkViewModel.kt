package com.example.noteapp2.presentation.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp2.common.ScreenViewState
import com.example.noteapp2.data.local.model.Note
import com.example.noteapp2.domain.use_cases.DeleteNoteUseCase
import com.example.noteapp2.domain.use_cases.FilteredBookmarkNotesUseCase
import com.example.noteapp2.domain.use_cases.UpdateNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val filteredBookmarkNotesUseCase: FilteredBookmarkNotesUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase
):ViewModel() {
    private val _state: MutableStateFlow<BookmarkState> = MutableStateFlow(BookmarkState())
    val state:StateFlow<BookmarkState> = _state.asStateFlow()

    init {
        getBookMarkedNotes()
    }

    private fun getBookMarkedNotes(){
        filteredBookmarkNotesUseCase().onEach {
            _state.value = BookmarkState(notes = ScreenViewState.Success(it))
        }.catch {
            _state.value = BookmarkState(notes = ScreenViewState.Error(it.message))
        }.launchIn(viewModelScope)
    }

    fun onBookmarkChange(note: Note){
        viewModelScope.launch {
            updateNoteUseCase(note.copy(isBookMarked = !note.isBookMarked))
        }
    }

    fun deleteNote(id:Long){
        viewModelScope.launch {
            deleteNoteUseCase(id)
        }
    }
}
data class BookmarkState(
    val notes : ScreenViewState<List<Note>> = ScreenViewState.Loading
)