package com.andreypmi.core_domain.usecase.wordsUseCases

import com.andreypmi.core_domain.models.Word
import com.andreypmi.core_domain.repository.WordRepository
import com.andreypmi.core_domain.usecase.UseCaseWithParam

class GetWordByNameUseCase(private val repository: WordRepository) :
    UseCaseWithParam<Word?, String> {
    override suspend fun execute(params: String): Word? {
        return repository.getWordByName(params)
    }
}