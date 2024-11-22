package com.andreypmi.dictionaryforwords.data.storage.entites

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = WordsEntity.TABLE_NAME)
data class WordsEntity(
    @PrimaryKey
    val id_word : Int,

    val id_category : Int,
    val word : String,
    val translate : String,
    val description : String
) {
    companion object{
        const val TABLE_NAME = "words"
        const val ID_WORD = "id_word"
        const val ID_CATEGORY = "id_category"
        const val WORD = "word"
        const val TRANSLATE = "translate"
        const val DESCRIPTION = "description"

    }
}