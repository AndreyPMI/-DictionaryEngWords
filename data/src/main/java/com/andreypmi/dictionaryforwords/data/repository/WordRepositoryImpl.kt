package com.andreypmi.dictionaryforwords.data.repository

import android.util.Log
import com.andreypmi.dictionaryforwords.data.mapper.EntityMapper
import com.andreypmi.dictionaryforwords.data.storage.dao.WordDao
import com.andreypmi.dictionaryforwords.domain.models.Word
import com.andreypmi.dictionaryforwords.domain.repository.WordRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList

class WordRepositoryImpl(
    private val dao: WordDao
) : WordRepository {
    override suspend fun getAllWords(): List<Word> {
        val list = dao.getAllWords().first().map { EntityMapper.toDomainModelForWord(it) }
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
        TODO("Not yet implemented")
    }

    override suspend fun update(word: Word): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun delete(word: Word): Boolean {
        TODO("Not yet implemented")
    }
}