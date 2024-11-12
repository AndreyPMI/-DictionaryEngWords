package com.andreypmi.dictionaryforwords.data.storage.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.andreypmi.dictionaryforwords.data.storage.entites.WordsEntity
import com.andreypmi.dictionaryforwords.domain.models.Word
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: WordsEntity)
    @Update
    suspend fun update(word: WordsEntity)
    @Delete
    suspend fun delete(word: WordsEntity)

    @Query("SELECT * FROM ${WordsEntity.TABLE_NAME} ORDER BY ${WordsEntity.ID_WORD} ASC")
    fun getAllWords(): Flow<List<WordsEntity>>

}