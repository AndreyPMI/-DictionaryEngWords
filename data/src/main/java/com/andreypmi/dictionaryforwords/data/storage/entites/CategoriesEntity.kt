package com.andreypmi.dictionaryforwords.data.storage.entites

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

    @Entity(tableName = CategoriesEntity.TABLE_NAME)
    data class CategoriesEntity(
        @PrimaryKey(autoGenerate = true)
        val id_category: Int,
        @NonNull
        val category_name: String
    ){
        companion object {
            const val TABLE_NAME = "categories"
            const val ID = "id_category"
            const val CATEGORY_NAME = "category_name"
        }
    }