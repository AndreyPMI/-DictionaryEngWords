package com.andreypmi.dictionaryforwords.domain.usecase

import com.andreypmi.dictionaryforwords.domain.models.Word
import com.andreypmi.dictionaryforwords.domain.repository.WordRepository

class WordUseCasesFacade(private val repository: WordRepository)  {
    private val getAllWordsUseCase = GetAllWordsUseCase(repository)
    private val getWordByIdUseCase = GetWordByIdUseCase(repository)
    private val getWordByNameUseCase = GetWordByNameUseCase(repository)
    private val insertWordUseCase = InsertWordUseCase(repository)
    private val updateWordUseCase = UpdateWordUseCase(repository)
    private val deleteWordUseCase = DeleteWordUseCase(repository)

    suspend fun getAllWords() = getAllWordsUseCase.execute()
    suspend fun getWordById(id: Long) = getWordByIdUseCase.execute(id)
    suspend fun getWordByName(name: String) = getWordByNameUseCase.execute(name)
    suspend fun insertWord(word: Word) = insertWordUseCase.execute(word)
    suspend fun updateWord(word: Word) = updateWordUseCase.execute(word)
    suspend fun deleteWord(word: Word) = deleteWordUseCase.execute(word)
}