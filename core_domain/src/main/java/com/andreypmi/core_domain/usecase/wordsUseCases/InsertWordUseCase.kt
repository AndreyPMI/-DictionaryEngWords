package com.andreypmi.core_domain.usecase.wordsUseCases

import com.andreypmi.core_domain.repository.WordRepository
import com.andreypmi.core_domain.models.Word
import com.andreypmi.core_domain.usecase.UseCaseWithParam

class InsertWordUseCase(private val repository: WordRepository) : UseCaseWithParam<Word?, Word> {
    override suspend fun execute(params: Word): Word? {
       return repository.insertWord(params)
    }
}