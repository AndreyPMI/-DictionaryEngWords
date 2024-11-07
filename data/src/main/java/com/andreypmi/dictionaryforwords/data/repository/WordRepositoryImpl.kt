package com.andreypmi.dictionaryforwords.data.repository

import com.andreypmi.dictionaryforwords.domain.models.Word
import com.andreypmi.dictionaryforwords.domain.repository.WordRepository

class WordRepositoryImpl : WordRepository {
    override suspend fun getAllWords(): List<Word> {
        TODO("Not yet implemented")
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