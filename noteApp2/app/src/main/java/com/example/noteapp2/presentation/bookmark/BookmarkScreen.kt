package com.example.noteapp2.presentation.bookmark

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.noteapp2.common.ScreenViewState
import com.example.noteapp2.data.local.model.Note
import com.example.noteapp2.presentation.home.NoteCard
import com.example.noteapp2.presentation.home.notes

@Composable
fun BookmarkScreen(
    state: BookmarkState,
    modifier: Modifier = Modifier,
    onBookmarkChange: (note: Note) -> Unit,
    onDelete:(Long) -> Unit,
    onNoteClicked:(Long) -> Unit
){
    when(state.notes){
        is ScreenViewState.Loading -> {
            CircularProgressIndicator()
        }
        is ScreenViewState.Success -> {
            val notes = state.notes.data
            LazyColumn(contentPadding = PaddingValues(4.dp),
                modifier = modifier,
                ){
                itemsIndexed(notes){ index:Int, note:Note ->
                    NoteCard(
                        index = index,
                        note = note,
                        onBookMarkChange = onBookmarkChange,
                        onDeleteNote = onDelete,
                        onNoteClicked = onNoteClicked,
                    )
                }
            }
        }
        is ScreenViewState.Error -> {
            Text(text = state.notes.message ?: "Unknown Error", color = MaterialTheme.colorScheme.error)
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PrevBookmark(){
    BookmarkScreen(
        state = BookmarkState(notes = ScreenViewState.Success(notes)),
        onBookmarkChange = {},
        onDelete = {},
        onNoteClicked = {}
    )
}