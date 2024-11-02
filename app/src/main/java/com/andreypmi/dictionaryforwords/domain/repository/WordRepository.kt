package com.andreypmi.dictionaryforwords.domain.repository

import com.andreypmi.dictionaryforwords.domain.models.Word

interface WordRepository {
    suspend fun getAllWords(): List<Word>
    suspend fun getWordById(id: Long): Word?
    suspend fun insert(word: Word) : Boolean
    suspend fun update(word: Word): Boolean
    suspend fun delete(word: Word): Boolean
}