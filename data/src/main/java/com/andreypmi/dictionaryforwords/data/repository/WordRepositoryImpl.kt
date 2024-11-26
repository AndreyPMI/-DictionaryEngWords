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
        Log.d("corrutine", "${list}")
        return list
    }

    override suspend fun getWordById(id: Long): Word? {
        TODO("Not yet implemented")
    }

    override suspend fun getWordByName(name: String): Word? {
        TODO("Not yet implemented")
    }

    override suspend fun insert(word: Word): Boolean {
        val newWordsEntity = WordsEntity(
            id_word = dao.getIndex().first() + 1,
            id_category = word.idCategory,
            word = word.word,
            description = word.description,
            translate = word.translate
        )
        Log.d("insert_repos" , "${newWordsEntity.toString()}")
        return try {
            dao.insert(word = newWordsEntity)
            true
        } catch (e: Throwable) {
            Log.e("Ошибка при вставке записи в базу данных.", "$e")
            false
        }
    }

    override suspend fun update(word: Word): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun delete(word: Word): Boolean {
        if (word.id == null){return false}
        val WordsEntity = WordsEntity(
            id_word = word.id!!,
            id_category = word.idCategory,
            word = word.word,
            description = word.description,
            translate = word.translate
        )
        return try {
            dao.delete(word = WordsEntity)
            true
        } catch (e: Throwable) {
            Log.e("Ошибка при вставке записи в базу данных.", "$e")
            false
        }
    }
}