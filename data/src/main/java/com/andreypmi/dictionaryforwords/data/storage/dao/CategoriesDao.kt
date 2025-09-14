package com.andreypmi.dictionaryforwords.data.storage.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.andreypmi.dictionaryforwords.data.storage.entites.CategoriesEntity
import com.andreypmi.dictionaryforwords.data.storage.entites.WordsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoriesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: CategoriesEntity): Long

    @Update
    suspend fun updateCategory(category: CategoriesEntity)

    @Delete
    suspend fun deleteCategory(category: CategoriesEntity)

    @Transaction
    suspend fun deleteCategoryCascade(category: CategoriesEntity) {
        deleteWordsByCategoryId(category.id_category)
        deleteCategory(category)
    }

    @Query("SELECT * FROM ${CategoriesEntity.TABLE_NAME} WHERE ${CategoriesEntity.ID} = :categoryId")
    suspend fun getCategoryById(categoryId: Int): CategoriesEntity?

    @Query("SELECT * FROM ${CategoriesEntity.TABLE_NAME}")
    fun getAllCategories(): Flow<List<CategoriesEntity>>

    @Query("DELETE FROM words WHERE ${WordsEntity.ID_CATEGORY} = :categoryId")
    suspend fun deleteWordsByCategoryId(categoryId: Int)
}