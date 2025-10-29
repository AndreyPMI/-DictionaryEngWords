package com.andreypmi.dictionaryforwords.data.bd.repository

import android.util.Log
import com.andreypmi.core_domain.models.Category
import com.andreypmi.core_domain.models.CategoryWithWordCount
import com.andreypmi.core_domain.models.Word
import com.andreypmi.core_domain.repository.PreferencesDataSource
import com.andreypmi.core_domain.repository.WordRepository
import com.andreypmi.dictionaryforwords.data.bd.mapper.EntityMapper
import com.andreypmi.dictionaryforwords.data.bd.storage.dao.CategoriesDao
import com.andreypmi.dictionaryforwords.data.bd.storage.dao.WordDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class WordRepositoryImpl @Inject constructor(
    private val wordDao: WordDao,
    private val categoriesDao: CategoriesDao,
    private val preferencesDataSource: PreferencesDataSource
) : WordRepository {

    override fun getWordsByCategoryId(categoryId: String): Flow<List<Word>> {
        return wordDao.getWordsByCategoryId(categoryId).map { entities ->
            entities.map { entity ->
                EntityMapper.toDomainModel(entity) }
        }
    }

    override suspend fun insertWord(word: Word): Word? {
        return try {
            val wordWithId = if (word.id.isNullOrBlank()) {
                word.copy(id = UUID.randomUUID().toString())
            } else {
                word
            }
            val entity = EntityMapper.fromDomainModel(wordWithId)
            wordDao.insert(entity)
            wordWithId
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun updateWord(word: Word): Boolean {
        return try {
            if (word.id.isNullOrBlank()) return false
            val entity = EntityMapper.fromDomainModel(word)
            wordDao.update(entity)
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun deleteWord(word: Word): Boolean {
        return try {
            if (word.id.isNullOrBlank()) return false

            val entity = EntityMapper.fromDomainModel(word)
            wordDao.delete(entity)
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun getAllCategories(): Flow<List<Category>> =
        categoriesDao.getAllCategories().map { categoriesEntities ->
            categoriesEntities.map {
                EntityMapper.toDomainModel(it)
            }
        }

    override suspend fun insertCategory(category: Category): Category? {
        return try {
            val categoryWithId = if (category.id.isBlank()) {
                category.copy(id = UUID.randomUUID().toString())
            } else {
                category
            }

            val entity = EntityMapper.fromDomainModel(categoryWithId)
            categoriesDao.insertCategory(entity)
            categoryWithId
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun updateCategory(category: Category): Boolean {
        return try {
            if (category.id.isBlank()) return false

            val entity = EntityMapper.fromDomainModel(category)
            categoriesDao.updateCategory(entity)
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun deleteCategory(category: Category): Boolean {
        return try {
            if (category.id.isBlank()) return false

            val entity = EntityMapper.fromDomainModel(category)
            categoriesDao.deleteCategoryCascade(entity)
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun getCategoryById(id: String): Category? {
        return categoriesDao.getCategoryById(id)?.let { EntityMapper.toDomainModel(it) }
    }

    override suspend fun saveLastSelectedCategory(key: String, category: Category) {
        try {
            preferencesDataSource.setValue(key, category.id)
        } catch (_: Exception) {
        }
    }

    override suspend fun loadLastSelectedCategory(key: String): String? {
        return try {
            preferencesDataSource.getValue<String?>(
                key = key,
                defaultValue = null
            )
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getCategoriesWithWordCount(offset: Int, limit: Int): List<CategoryWithWordCount> {
        return try {
            val models = categoriesDao.getCategoriesWithWordCount(offset, limit)
            models.map { model -> EntityMapper.toDomainModel(model) }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getCategoriesCount(): Int {
        return try {
            categoriesDao.getCategoriesCount()
        } catch (e: Exception) {
            0
        }
    }

}