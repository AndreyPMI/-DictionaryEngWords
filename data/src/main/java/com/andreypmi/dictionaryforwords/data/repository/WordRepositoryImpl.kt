package com.andreypmi.dictionaryforwords.data.repository

import android.util.Log
import com.andreypmi.core_domain.models.Category
import com.andreypmi.core_domain.models.Word
import com.andreypmi.core_domain.repository.PreferencesDataSource
import com.andreypmi.core_domain.repository.WordRepository
import com.andreypmi.dictionaryforwords.data.mapper.EntityMapper
import com.andreypmi.dictionaryforwords.data.storage.dao.CategoriesDao
import com.andreypmi.dictionaryforwords.data.storage.dao.WordDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WordRepositoryImpl @Inject constructor(
    private val wordDao: WordDao,
    private val categoriesDao: CategoriesDao,
    private val preferencesDataSource: PreferencesDataSource
) : WordRepository {

    override fun getWordsByCategoryId(category: Category): Flow<List<Word>> {
        return wordDao.getWordsByCategoryId(category.id).map { entities ->
            entities.map { entity -> EntityMapper.toDomainModel(entity) }
        }
    }

    override suspend fun insertWord(word: Word): Word? {
        val newWordsEntity = EntityMapper.fromDomainModel(word, wordDao.getIndex().first() + 1)
        return try {
            wordDao.insert(word = newWordsEntity)
            EntityMapper.toDomainModel(newWordsEntity)
        } catch (e: Throwable) {
            null
        }
    }

    override suspend fun updateWord(word: Word): Boolean {
        val newWordsEntity = EntityMapper.fromDomainModel(word, id = word.id!!)
        return try {
            wordDao.insert(newWordsEntity)
            true
        } catch (e: Throwable) {
            false
        }
    }

    override suspend fun deleteWord(word: Word): Boolean {
        if (word.id == null) {
            return false
        }
        val wordsEntity = EntityMapper.fromDomainModel(word, word.id!!)
        return try {
            wordDao.delete(word = wordsEntity)
            true
        } catch (e: Throwable) {
            Log.e("Ошибка при вставке записи в базу данных.", "$e")
            false
        }
    }

    override fun getAllCategory(): Flow<List<Category>> =
        categoriesDao.getAllCategories().map { categoriesEntitys ->
            categoriesEntitys.map {
                EntityMapper.toDomainModel(it)
            }
        }

    override suspend fun insertCategory(category: Category): Category? {
        try {
            val id = categoriesDao.insertCategory(EntityMapper.fromDomainModel(category))
            return category.copy(id = id.toInt())
        } catch (e: Exception) {
            return null
        }
    }

    override suspend fun updateCategory(category: Category): Boolean {
        try {
            categoriesDao.updateCategory(EntityMapper.fromDomainModel(category))
            return true
        } catch (e: Exception) {
            return false
        }
    }

    override suspend fun deleteCategory(category: Category): Boolean {
        try {
            categoriesDao.deleteCategoryCascade(EntityMapper.fromDomainModel(category))
            return true
        } catch (e: Exception) {
            return false
        }
    }

    override suspend fun getWordById(id: Long): Word? {
        TODO("Not yet implemented")
    }

    override suspend fun saveLastSelectedCategory(key: String, category: Category) {
        preferencesDataSource.setValue(
            key = APP_PREFERENCES,
            value = category.id
        )
    }

    override suspend fun loadLastSelectedCategory(key: String): String? =
        preferencesDataSource.getValue<String?>(
            key = APP_PREFERENCES,
            defaultValue = null
        )


}