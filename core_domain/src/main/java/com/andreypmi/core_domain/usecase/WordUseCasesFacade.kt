package com.andreypmi.core_domain.usecase

import com.andreypmi.core_domain.models.Category
import com.andreypmi.core_domain.models.Word
import com.andreypmi.core_domain.repository.WordRepository
import com.andreypmi.core_domain.usecase.wordsUseCases.DeleteWordUseCase
import com.andreypmi.core_domain.usecase.wordsUseCases.GetAllWordsUseCase
import com.andreypmi.core_domain.usecase.wordsUseCases.InsertWordUseCase
import com.andreypmi.core_domain.usecase.wordsUseCases.UpdateWordUseCase
import javax.inject.Inject

class WordUseCasesFacade @Inject constructor(
  repository: WordRepository
)  {
    private val getAllWordsUseCase = GetAllWordsUseCase(repository)
    private val insertWordUseCase = InsertWordUseCase(repository)
    private val updateWordUseCase = UpdateWordUseCase(repository)
    private val deleteWordUseCase = DeleteWordUseCase(repository)

    suspend fun getAllWords(categoryId: String) = getAllWordsUseCase.execute(categoryId)
    suspend fun insertWord(word: Word) = insertWordUseCase.execute(word)
    suspend fun updateWord(word: Word) = updateWordUseCase.execute(word)
    suspend fun deleteWord(word: Word) = deleteWordUseCase.execute(word)
}