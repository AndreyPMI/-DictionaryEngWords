package com.andreypmi.dictionaryforwords.domain.usecase

import com.andreypmi.dictionaryforwords.domain.models.Word
import com.andreypmi.dictionaryforwords.domain.repository.WordRepository

class GetWordByNameUseCase(private val repository: WordRepository) :
    UseCaseWithParam<Word?, String> {
    override suspend fun execute(Name: String): Word? {
        return repository.getWordByName(Name)
    }
}