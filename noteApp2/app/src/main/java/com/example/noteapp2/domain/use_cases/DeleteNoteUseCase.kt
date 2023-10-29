package com.example.noteapp2.domain.use_cases

import com.example.noteapp2.domain.repository.Repository
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke(id:Long) = repository.delete(id)
}