package com.andreypmi.dictionaryforwords.domain.usecase

import com.andreypmi.dictionaryforwords.domain.models.Word
import com.andreypmi.dictionaryforwords.domain.repository.WordRepository

class UpdateWordUseCase(private val repository: WordRepository) : UseCaseWithParam<Boolean, Word> {
    override suspend fun execute(Params: Word): Boolean {
        return repository.update(Params)
    }
}