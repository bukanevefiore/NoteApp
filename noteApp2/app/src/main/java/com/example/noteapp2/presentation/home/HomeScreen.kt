@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.noteapp2.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkRemove
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.BookmarkAdd
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.noteapp2.common.ScreenViewState
import com.example.noteapp2.data.local.model.Note
import java.util.Date


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    state: HomeState,
    onBookMarkChange: (note:Note) -> Unit,
    onDeleteNote: (Long) -> Unit,
    onNoteClicked: (Long) -> Unit
){
    when(state.notes){
        is ScreenViewState.Loading -> {
            CircularProgressIndicator()
        }
        is ScreenViewState.Success -> {
            val notes = state.notes.data
            HomeDetail(
                notes = notes,
                modifier = modifier,
                onBookMarkChange = onBookMarkChange,
                onDeleteNote = onDeleteNote,
                onNoteClicked = onNoteClicked,
            )
        }
        is ScreenViewState.Error -> {
            Text(
                text = state.notes.message ?: "Unknown Error",
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

@Composable
private fun HomeDetail(
    notes:List<Note>,
    modifier: Modifier,
    onBookMarkChange:(note:Note) -> Unit,
    onDeleteNote:(Long) -> Unit,
    onNoteClicked:(Long) -> Unit
){
    LazyVerticalStaggeredGrid(columns = StaggeredGridCells.Fixed(2),
        contentPadding = PaddingValues(4.dp), modifier = modifier
    ) {
        itemsIndexed(notes) { index, item ->
            NoteCard(
                index = index,
                note = item,
                onBookMarkChange = onBookMarkChange,
                onDeleteNote = onDeleteNote,
                onNoteClicked = onNoteClicked
            )
        }
    }



}

@Composable
fun NoteCard(
    index:Int,
    note:Note,
    onBookMarkChange: (note: Note) -> Unit,
    onDeleteNote: (Long) -> Unit,
    onNoteClicked: (Long) -> Unit
) {
    val isEvenIndex = index % 2 == 0
    val shape = when {
        isEvenIndex -> {
            RoundedCornerShape(
                topStart = 50f,
                bottomEnd = 50f
            )
        } else -> {
            RoundedCornerShape(
                topEnd = 50f,
                bottomStart = 50f
            )
        }
    }
    val icon = if(note.isBookMarked) Icons.Default.BookmarkRemove
    else Icons.Outlined.BookmarkAdd

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        shape = shape,
        onClick = {onNoteClicked(note.id)}
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(text = note.title, fontWeight = FontWeight.Bold, maxLines = 1,
                style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.size(4.dp))
            Text(text = note.content, maxLines = 4, overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { onDeleteNote(note.id) }) {
                    Icon(imageVector = Icons.Default.Delete,
                        contentDescription = null)
                }
                IconButton(onClick = { onBookMarkChange(note) }) {
                    Icon(imageVector = icon, contentDescription = null)
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun prevHome(){
    HomeScreen(
        state = HomeState(
            notes = ScreenViewState.Success(notes)
        ),
        onBookMarkChange = {}, onDeleteNote = {}, onNoteClicked = {})
}

val placeHolderText = "Lorem ipsum dolor sit amet, consectetur adipisicing elitn"

val notes = listOf(
    Note(
        title = "Room",
        content = placeHolderText,
        createdDate = Date()
    ),
    Note(
        title = "Room",
        content = "Testing",
        createdDate = Date(),
        isBookMarked = true
    ),
    Note(
        title = "Room",
        content = placeHolderText,
        createdDate = Date(),
        isBookMarked = true
    ),
    Note(
        title = "Room",
        content = placeHolderText+ placeHolderText,
        createdDate = Date()
    ),
    Note(
        title = "Room",
        content = "Testing",
        createdDate = Date(),
        isBookMarked = true
    ),
    Note(
        title = "Room",
        content = placeHolderText,
        createdDate = Date(),
        isBookMarked = true
    ),
    Note(
        title = "Room",
        content = placeHolderText+ placeHolderText,
        createdDate = Date()
    )

)