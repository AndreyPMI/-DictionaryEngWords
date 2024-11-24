package com.andreypmi.dictionaryforwords.data.mapper

import com.andreypmi.dictionaryforwords.data.storage.entites.CategoriesEntity
import com.andreypmi.dictionaryforwords.data.storage.entites.WordsEntity
import com.andreypmi.dictionaryforwords.domain.models.Category
import com.andreypmi.dictionaryforwords.domain.models.Word

object EntityMapper {
    fun toDomainModel(entity: WordsEntity): Word {
        return Word(
            id = entity.id_word,
            idCategory = entity.id_category,
            word = entity.word,
            translate = entity.translate,
            description = entity.description?:""
        )
    }
    fun toDomainModel(entity: CategoriesEntity): Category {
        return Category(
            id = entity.id_category,
            category = entity.category_name
        )
    }
}