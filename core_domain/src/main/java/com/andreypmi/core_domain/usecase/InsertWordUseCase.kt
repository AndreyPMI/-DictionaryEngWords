package com.andreypmi.core_domain.usecase

import com.andreypmi.core_domain.repository.WordRepository
import com.andreypmi.core_domain.models.Word

class InsertWordUseCase(private val repository: WordRepository) : UseCaseWithParam<Word?, Word> {
    override suspend fun execute(params: Word): Word? {
       return repository.insert(params)
    }
}