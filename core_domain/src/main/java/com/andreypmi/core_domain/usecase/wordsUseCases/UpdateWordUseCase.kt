package com.andreypmi.core_domain.usecase.wordsUseCases

import com.andreypmi.core_domain.models.Word
import com.andreypmi.core_domain.repository.WordRepository
import com.andreypmi.core_domain.usecase.UseCaseWithParam

class UpdateWordUseCase(private val repository: WordRepository) : UseCaseWithParam<Boolean, Word> {
    override suspend fun execute(params: Word): Boolean {
        return repository.updateWord(params)
    }
}