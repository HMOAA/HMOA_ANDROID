package com.hyangmoa.core_domain.usecase

import com.hyangmoa.core_domain.repository.NoteRepository
import com.hyangmoa.core_model.response.NoteDescResponseDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetNoteUseCase @Inject constructor(private val noteRepository: NoteRepository) {
    suspend operator fun invoke(id: Int): Flow<NoteDescResponseDto?> {
        val result = noteRepository.getNote(id)
        return flow {
            if (result.errorMessage != null) {
                throw Exception(result.errorMessage!!.message)
            }
            emit(result.data?.data)
        }
    }
}