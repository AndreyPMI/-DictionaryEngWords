package com.andreypmi.dictionaryforwords.data.mapper

import com.andreypmi.dictionaryforwords.data.storage.entites.CategoriesEntity
import com.andreypmi.dictionaryforwords.data.storage.entites.WordsEntity
import com.andreypmi.dictionaryforwords.domain.models.Category
import com.andreypmi.dictionaryforwords.domain.models.Word

object EntityMapper {
    fun toDomainModelForWord(entity: WordsEntity): Word {
        return Word(
            id = entity.id_word,
            word = entity.word,
            translate = entity.translate,
            description = entity.description
        )
    }
    fun toDomainModelForCategory(entity: CategoriesEntity): Category {
        return Category(
            id = entity.id_category,
            category = entity.category_name
        )
    }
}