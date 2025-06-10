package com.andreypmi.core_domain.usecase

import com.andreypmi.core_domain.models.Word
import com.andreypmi.core_domain.repository.WordRepository

class GetWordByIdUseCase(private val repository: WordRepository): UseCaseWithParam<Word?, Long> {
    override suspend fun execute(params: Long): Word? {
        return repository.getWordById(params)
    }
}