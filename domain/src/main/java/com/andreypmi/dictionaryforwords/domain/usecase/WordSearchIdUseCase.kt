package com.andreypmi.dictionaryforwords.domain.usecase

import com.andreypmi.dictionaryforwords.domain.models.Word
import com.andreypmi.dictionaryforwords.domain.repository.WordRepository

class WordSearchIdUseCase(private val repository: WordRepository): UseCaseWithParam<Word?, Long> {
    override suspend fun execute(wordId: Long): Word? {
        return repository.getWordById(wordId)
    }
}