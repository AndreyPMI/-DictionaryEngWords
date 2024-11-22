package com.andreypmi.dictionaryforwords.data.storage.entites

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = WordsEntity.TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = CategoriesEntity::class,
            parentColumns = arrayOf(CategoriesEntity.ID),
            childColumns = arrayOf(WordsEntity.ID_CATEGORY),
            onDelete = ForeignKey.CASCADE
        )
    ])
data class WordsEntity(
    @PrimaryKey(autoGenerate = true)
    val id_word : Int,
    @NonNull
    val id_category : Int,
    @NonNull
    val word : String,
    @NonNull
    val translate : String,

    val description : String?
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