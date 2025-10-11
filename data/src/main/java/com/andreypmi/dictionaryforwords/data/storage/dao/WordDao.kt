package com.andreypmi.dictionaryforwords.data.storage.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.andreypmi.core_domain.models.Category
import com.andreypmi.dictionaryforwords.data.storage.entites.WordsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(word: WordsEntity)
    @Update
    suspend fun update(word: WordsEntity)
    @Delete
    suspend fun delete(word: WordsEntity)

    @Query("SELECT * FROM ${WordsEntity.TABLE_NAME} WHERE ${WordsEntity.ID_CATEGORY} = :categoryId ORDER BY ${WordsEntity.WORD} ASC")
    fun getWordsByCategoryId(categoryId: String): Flow<List<WordsEntity>>
}