package com.example.noteapp2.domain.use_cases

import com.example.noteapp2.data.local.model.Note
import com.example.noteapp2.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNoteByIdUseCase @Inject constructor(
    private val repository: Repository
) {
    operator fun invoke(id:Long) : Flow<Note> = repository.getNoteById(id)

}