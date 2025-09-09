package com.andreypmi.core_domain.usecase.wordsUseCases

import com.andreypmi.core_domain.models.Word
import com.andreypmi.core_domain.repository.WordRepository
import com.andreypmi.core_domain.usecase.UseCaseWithParam

class GetWordByIdUseCase(private val repository: WordRepository): UseCaseWithParam<Word?, Long> {
    override suspend fun execute(params: Long): Word? {
        return repository.getWordById(params)
    }
}