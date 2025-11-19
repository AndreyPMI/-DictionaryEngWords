package com.andreypmi.core_domain.usecase.categoryUseCases

import com.andreypmi.core_domain.models.Category
import com.andreypmi.core_domain.models.Word
import com.andreypmi.core_domain.repository.WordRepository
import javax.inject.Inject

class InsertCategoryWithWordsUseCase @Inject constructor(private val repository: WordRepository) {
    suspend fun execute(category: Category, words: List<Word>): Boolean {
        val insertedCategory = repository.insertCategory(category) ?: return false

        val wordsWithCategoryId = words.map { it.copy(idCategory = insertedCategory.id) }
        repository.insertWords(wordsWithCategoryId)

        return true
    }
}