package com.andreypmi.core_domain.usecase

import com.andreypmi.core_domain.models.Word
import com.andreypmi.core_domain.repository.WordRepository

class UpdateWordUseCase(private val repository: WordRepository) : UseCaseWithParam<Boolean, Word> {
    override suspend fun execute(params: Word): Boolean {
        return repository.update(params)
    }
}