package com.andreypmi.dictionaryforwords.data.repository

import android.util.Log
import com.andreypmi.dictionaryforwords.data.mapper.EntityMapper
import com.andreypmi.dictionaryforwords.data.storage.dao.WordDao
import com.andreypmi.dictionaryforwords.data.storage.entites.WordsEntity
import com.andreypmi.dictionaryforwords.domain.models.Word
import com.andreypmi.dictionaryforwords.domain.repository.WordRepository
import kotlinx.coroutines.flow.first

class WordRepositoryImpl(
    private val dao: WordDao
) : WordRepository {
    override suspend fun getAllWords(): List<Word> {
        val list = dao.getAllWords().first().map { EntityMapper.toDomainModel(it) }
        return list
    }

    override suspend fun getWordById(id: Long): Word? {
        TODO("Not yet implemented")
    }

    override suspend fun getWordByName(name: String): Word? {
        TODO("Not yet implemented")
    }

    override suspend fun insert(word: Word): Word? {
        val newWordsEntity = EntityMapper.fromDomainModel(word, dao.getIndex().first() + 1)
        Log.d("insert_repos", "${newWordsEntity.toString()}")
        return try {
            dao.insert(word = newWordsEntity)
            EntityMapper.toDomainModel(newWordsEntity)
        } catch (e: Throwable) {
            Log.e("Ошибка при вставке записи в базу данных.", "$e")
            null
        }
    }

    override suspend fun update(word: Word): Boolean {
        val newWordsEntity = EntityMapper.fromDomainModel(word, id = word.id!!)
        return try {
            dao.insert(newWordsEntity)
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
        dao.delete(word = WordsEntity)
        true
    } catch (e: Throwable) {
        Log.e("Ошибка при вставке записи в базу данных.", "$e")
        false
    }
}
}