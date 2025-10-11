package com.andreypmi.core_domain.repository

import com.andreypmi.core_domain.models.Category
import com.andreypmi.core_domain.models.CategoryWithWordCount
import com.andreypmi.core_domain.models.Word
import kotlinx.coroutines.flow.Flow

interface WordRepository {
    fun getWordsByCategoryId(categoryId: String): Flow<List<Word>>
    suspend fun insertWord(word: Word): Word?
    suspend fun updateWord(word: Word): Boolean
    suspend fun deleteWord(word: Word): Boolean

    fun getAllCategories(): Flow<List<Category>>
    suspend fun insertCategory(category: Category): Category?
    suspend fun updateCategory(category: Category): Boolean
    suspend fun deleteCategory(category: Category): Boolean
    suspend fun getCategoryById(id: String): Category?

    suspend fun saveLastSelectedCategory(key: String, category: Category)
    suspend fun loadLastSelectedCategory(key: String): String?

    suspend fun getCategoriesWithWordCount(offset: Int, limit: Int): List<CategoryWithWordCount>
    suspend fun getCategoriesCount(): Int
}