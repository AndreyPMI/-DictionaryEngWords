package com.andreypmi.dictionaryforwords.data.mapper

import com.andreypmi.core_domain.models.Category
import com.andreypmi.core_domain.models.Word
import com.andreypmi.dictionaryforwords.data.storage.entites.CategoriesEntity
import com.andreypmi.dictionaryforwords.data.storage.entites.WordsEntity

object EntityMapper {
    fun toDomainModel(entity: WordsEntity): Word {
        return Word(
            id = entity.id_word,
            idCategory = entity.id_category,
            word = entity.word,
            translate = entity.translate,
            description = entity.description ?: ""
        )
    }
    fun toDomainModel(entity: CategoriesEntity): Category {
        return Category(
            id = entity.id_category,
            category = entity.category_name
        )
    }
    fun fromDomainModel(model: Word, id:Int):WordsEntity{
        return WordsEntity(
            id_word = model.id?:id,
            id_category = model.idCategory,
            word = model.word,
            translate = model.translate,
            description = model.description
        )
    }
    fun fromDomainModel(model: Category):CategoriesEntity{
        return CategoriesEntity(
            id_category = model.id,
            category_name = model.category
        )
    }
}