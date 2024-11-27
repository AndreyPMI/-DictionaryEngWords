package com.andreypmi.dictionaryforwords.domain.usecase

import com.andreypmi.dictionaryforwords.domain.repository.WordRepository
import com.andreypmi.dictionaryforwords.domain.models.Word

class InsertWordUseCase(private val repository: WordRepository) : UseCaseWithParam<Word?, Word> {
    override suspend fun execute(params: Word): Word? {
       return repository.insert(params)
    }
}