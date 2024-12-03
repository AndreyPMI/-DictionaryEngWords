package com.andreypmi.dictionaryforwords.domain.repository

import com.andreypmi.dictionaryforwords.domain.models.Word
import kotlinx.coroutines.flow.Flow

interface WordRepository {
    suspend fun getAllWords(): Flow<List<Word>>
    suspend fun getWordById(id: Long): Word?
    suspend fun getWordByName(name: String): Word?
    suspend fun insert(word: Word) : Word?
    suspend fun update(word: Word): Boolean
    suspend fun delete(word: Word): Boolean
}