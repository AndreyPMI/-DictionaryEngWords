package com.andreypmi.dictionaryforwords.domain.usecase

import com.andreypmi.dictionaryforwords.domain.models.Word
import com.andreypmi.dictionaryforwords.domain.repository.WordRepository
import kotlinx.coroutines.flow.Flow

class GetAllWordsUseCase(private val repository: WordRepository) : UseCaseWithoutParam<Flow<List<Word>>> {
    override suspend fun execute(): Flow<List<Word>> {
        return repository.getAllWords()
    }
}



