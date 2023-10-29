package com.example.noteapp2.domain.use_cases

import com.example.noteapp2.data.local.model.Note
import com.example.noteapp2.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllNotesUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(): Flow<List<Note>> = repository.getAllNotes()

}