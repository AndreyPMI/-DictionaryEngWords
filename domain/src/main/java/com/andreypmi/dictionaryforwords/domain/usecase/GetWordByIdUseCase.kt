package com.andreypmi.dictionaryforwords.domain.usecase

import com.andreypmi.dictionaryforwords.domain.models.Word
import com.andreypmi.dictionaryforwords.domain.repository.WordRepository

class GetWordByIdUseCase(private val repository: WordRepository): UseCaseWithParam<Word?, Long> {
    override suspend fun execute(Params: Long): Word? {
        return repository.getWordById(Params)
    }
}