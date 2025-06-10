package com.andreypmi.core_domain.usecase

import com.andreypmi.core_domain.models.Word
import com.andreypmi.core_domain.repository.WordRepository
import kotlinx.coroutines.flow.Flow

class GetAllWordsUseCase(private val repository: WordRepository) :
    UseCaseWithoutParam<Flow<List<Word>>> {
    override suspend fun execute(): Flow<List<Word>> {
        return repository.getAllWords()
    }
}



