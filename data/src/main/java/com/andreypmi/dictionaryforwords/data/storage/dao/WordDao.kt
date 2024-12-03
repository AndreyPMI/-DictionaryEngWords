package com.andreypmi.dictionaryforwords.data.storage.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.andreypmi.dictionaryforwords.data.storage.entites.CategoriesEntity
import com.andreypmi.dictionaryforwords.data.storage.entites.WordsEntity
import com.andreypmi.dictionaryforwords.domain.models.Word
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(word: WordsEntity)
    @Update
    suspend fun update(word: WordsEntity)
    @Delete
    suspend fun delete(word: WordsEntity)

   // @Query("SELECT ${WordsEntity.ID_WORD},${CategoriesEntity.CATEGORY_NAME}, ${WordsEntity.WORD}, ${WordsEntity.TRANSLATE}, ${WordsEntity.DESCRIPTION} FROM ${WordsEntity.TABLE_NAME}\n" +
     //       "join ${CategoriesEntity.TABLE_NAME} WHERE ${WordsEntity.TABLE_NAME}.${WordsEntity.ID_CATEGORY} = ${CategoriesEntity.TABLE_NAME}.${CategoriesEntity.ID} ORDER BY ${WordsEntity.ID_WORD} ASC ")
    @Query("SELECT * FROM ${WordsEntity.TABLE_NAME} ORDER BY ${WordsEntity.ID_WORD} ASC")
    fun getAllWords(): Flow<List<WordsEntity>>

    @Query("SELECT MAX(${WordsEntity.ID_WORD}) FROM ${WordsEntity.TABLE_NAME}")
    fun getIndex(): Flow<Int>
}