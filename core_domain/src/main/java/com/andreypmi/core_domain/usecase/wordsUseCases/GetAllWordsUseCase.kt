package com.andreypmi.core_domain.usecase.wordsUseCases

import com.andreypmi.core_domain.models.Category
import com.andreypmi.core_domain.models.Word
import com.andreypmi.core_domain.repository.WordRepository
import com.andreypmi.core_domain.usecase.UseCaseWithParam
import com.andreypmi.core_domain.usecase.UseCaseWithoutParam
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllWordsUseCase @Inject constructor(private val repository: WordRepository) :
    UseCaseWithParam<Flow<List<Word>>,Category> {
    override suspend fun execute(params: Category): Flow<List<Word>>{
        return repository.getWordsByCategoryId(params)
    }
}



