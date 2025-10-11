package com.andreypmi.dictionaryforwords.data.storage.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.andreypmi.dictionaryforwords.data.storage.dto.CategoryWithWordCountModel
import com.andreypmi.dictionaryforwords.data.storage.entites.CategoriesEntity
import com.andreypmi.dictionaryforwords.data.storage.entites.WordsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoriesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: CategoriesEntity)

    @Update
    suspend fun updateCategory(category: CategoriesEntity)

    @Delete
    suspend fun deleteCategory(category: CategoriesEntity)

    @Query("DELETE FROM ${CategoriesEntity.TABLE_NAME} WHERE ${CategoriesEntity.ID} = :id")
    suspend fun deleteCategoryCascade(id: String)


    @Transaction
    suspend fun deleteCategoryCascade(category: CategoriesEntity) {
        deleteWordsByCategoryId(category.id)
        deleteCategory(category)
    }

    @Query("SELECT * FROM ${CategoriesEntity.TABLE_NAME} WHERE ${CategoriesEntity.ID} = :id")
    suspend fun getCategoryById(id: String): CategoriesEntity?

    @Query("SELECT * FROM ${CategoriesEntity.TABLE_NAME} ORDER BY ${CategoriesEntity.CATEGORY_NAME} ASC")
    fun getAllCategories(): Flow<List<CategoriesEntity>>

    @Query("""
        SELECT 
            categories.${CategoriesEntity.ID},
            categories.${CategoriesEntity.CATEGORY_NAME}, 
            COUNT(words.${WordsEntity.ID_WORD}) as wordCount
        FROM ${CategoriesEntity.TABLE_NAME} categories 
        LEFT JOIN ${WordsEntity.TABLE_NAME} words 
            ON categories.${CategoriesEntity.ID} = words.${WordsEntity.ID_CATEGORY} 
        GROUP BY categories.${CategoriesEntity.ID} 
        ORDER BY categories.${CategoriesEntity.CATEGORY_NAME} ASC
        LIMIT :limit OFFSET :offset
    """)
    suspend fun getCategoriesWithWordCount(offset: Int, limit: Int): List<CategoryWithWordCountModel>

    @Query("SELECT COUNT(*) FROM ${CategoriesEntity.TABLE_NAME}")
    suspend fun getCategoriesCount(): Int

    @Query("DELETE FROM ${WordsEntity.TABLE_NAME} WHERE ${WordsEntity.ID_CATEGORY} = :categoryId")
    suspend fun deleteWordsByCategoryId(categoryId: String)
}