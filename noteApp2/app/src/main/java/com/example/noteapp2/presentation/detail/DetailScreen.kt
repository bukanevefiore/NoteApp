@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.noteapp2.presentation.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.BookmarkRemove
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Update
import androidx.compose.material.icons.outlined.BookmarkAdd
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    noteId: Long,
    assistedFactory: DetailAssistedFactory,
    navigationUp: () -> Unit,
){
    val viewModel = viewModel(
        DetailViewModel::class.java,
        factory = DetailViewModelFactory(noteId = noteId, assistedFactory)
    )
    val state = viewModel.state
    DetailScreen(
        modifier = modifier,
        isUpdatingNote =  state.isUpdatingNote,
        title = state.title,
        content = state.content,
        isBookMark = state.isBookmark,
        onBookmarkChange = viewModel::onBookmarkChange,
        isFormNotBlank = state.isUpdatingNote,
        onTitleChange = viewModel::onTitleChange,
        onContentChange = viewModel::onContentChange,
        onBtnClick = {
            viewModel.addOrUpdateNote()
            navigationUp()
        },
        onNavigate = navigationUp
    )
}
@Composable
private fun DetailScreen(
    modifier: Modifier,
    isUpdatingNote: Boolean,
    title: String,
    content: String,
    isBookMark: Boolean,
    onBookmarkChange: (Boolean) -> Unit,
    isFormNotBlank: Boolean,
    onTitleChange: (String) -> Unit,
    onContentChange: (String) -> Unit,
    onBtnClick: () -> Unit,
    onNavigate: () -> Unit
){
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        TopSection(
            title = title,
            isBookMark = isBookMark,
            onBookmarkChange = onBookmarkChange,
            onTitleChange = onTitleChange,
            onNavigate = onNavigate
        )
        Spacer(modifier = Modifier.Companion.size(4.dp))
        AnimatedVisibility(isFormNotBlank) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = { onBtnClick }) {
                    val icon = if(isUpdatingNote) Icons.Default.Update
                    else Icons.Default.Check
                    Icon(imageVector = icon, contentDescription = null)
                }
            }
        }
        Spacer(modifier = Modifier.size(12.dp))
        NotesTextField(
            modifier = Modifier.weight(1f),
            value = content,
            onValueChange = onContentChange,
            label = "Content"
        )
    }
}

@Composable
fun TopSection(
    modifier: Modifier = Modifier,
    title: String,
    isBookMark: Boolean,
    onBookmarkChange:(Boolean) -> Unit,
    onTitleChange:(String) -> Unit,
    onNavigate:() -> Unit
){
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { onNavigate }) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null
            )
            NotesTextField(
                modifier = modifier.weight(1f),
                value = title,
                onValueChange = onTitleChange,
                label = "Title",
                labelAlign = TextAlign.Center,
            )
            IconButton(onClick = { onBookmarkChange(!isBookMark) }) {
                val icon = if(isBookMark) Icons.Default.BookmarkRemove
                else Icons.Outlined.BookmarkAdd
                Icon(imageVector = icon, contentDescription = null)
            }
        }
    }
}

@Composable
private fun NotesTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange:(String) -> Unit,
    label: String,
    labelAlign: TextAlign? = null
){

    OutlinedTextField(value = value, onValueChange = onValueChange, modifier = modifier,
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            containerColor = Color.Transparent,
        ),
        placeholder = {
            Text(
                text = "Insert $label",
                textAlign = labelAlign,
                modifier = modifier.fillMaxWidth()
            )
        }
    )
}




