package com.andreypmi.dictionaryforwords.domain.usecase

import com.andreypmi.dictionaryforwords.domain.models.Word
import com.andreypmi.dictionaryforwords.domain.repository.WordRepository

class GetAllWordsUseCase(private val repository: WordRepository) : UseCaseWithoutParam<List<Word>> {
    override suspend fun execute(): List<Word> {
        return repository.getAllWords()
    }
}