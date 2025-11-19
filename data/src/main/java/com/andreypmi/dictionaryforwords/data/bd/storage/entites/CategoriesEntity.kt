package com.andreypmi.dictionaryforwords.data.bd.storage.entites

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

    @Entity(tableName = CategoriesEntity.TABLE_NAME)
    data class CategoriesEntity(
        @PrimaryKey
        val id: String,
        @NonNull
        val categoryName: String
    ){
        companion object {
            const val TABLE_NAME = "categories"
            const val ID = "id"
            const val CATEGORY_NAME = "categoryName"
        }
    }

