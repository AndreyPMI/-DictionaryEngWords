package com.andreypmi.dictionaryforwords.domain.usecase

import com.andreypmi.dictionaryforwords.domain.models.Word
import com.andreypmi.dictionaryforwords.domain.repository.WordRepository

class UpdateWordUseCase(private val repository: WordRepository) : UseCaseWithParam<Boolean, Word> {
    override suspend fun execute(word: Word): Boolean {
        return repository.update(word)
    }
}