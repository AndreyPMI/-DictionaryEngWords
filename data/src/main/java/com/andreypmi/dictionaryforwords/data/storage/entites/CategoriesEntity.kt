package com.andreypmi.dictionaryforwords.data.storage.entites

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = CategoriesEntity.TABLE_NAME)
data class CategoriesEntity(
    @PrimaryKey
    val id_category: Int,

    val category_name: String
){
    companion object {
        const val TABLE_NAME = "categories"
        const val ID = "id_category"
        const val CATEGORY_NAME = "category_name"
    }
}