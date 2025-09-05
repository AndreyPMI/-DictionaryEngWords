package com.andreypmi.dictionaryforwords.data.repository

import android.util.Log
import com.andreypmi.core_domain.models.Word
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
) : WordRepository {
    override suspend fun getAllWords(): Flow<List<Word>> {
        return wordDao.getAllWords().map { entities ->
            entities.map { entity -> EntityMapper.toDomainModel(entity) }
        }
    }

    override suspend fun getWordById(id: Long): Word? {
        TODO("Not yet implemented")
    }

    override suspend fun getWordByName(name: String): Word? {
        TODO("Not yet implemented")
    }

    override suspend fun insert(word: Word): Word? {
        val newWordsEntity = EntityMapper.fromDomainModel(word, wordDao.getIndex().first() + 1)
        return try {
            wordDao.insert(word = newWordsEntity)
            EntityMapper.toDomainModel(newWordsEntity)
        } catch (e: Throwable) {
            null
        }
    }

    override suspend fun update(word: Word): Boolean {
        val newWordsEntity = EntityMapper.fromDomainModel(word, id = word.id!!)
        return try {
            wordDao.insert(newWordsEntity)
            true
        } catch (e: Throwable) {
            false
        }
    }

override suspend fun delete(word: Word): Boolean {
    if (word.id == null) {
        return false
    }
    val WordsEntity = EntityMapper.fromDomainModel(word, word.id!!)
    return try {
        wordDao.delete(word = WordsEntity)
        true
    } catch (e: Throwable) {
        Log.e("Ошибка при вставке записи в базу данных.", "$e")
        false
    }
}
}