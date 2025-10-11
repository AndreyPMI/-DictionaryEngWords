package com.andreypmi.dictionaryforwords.data.mapper

import com.andreypmi.core_domain.models.Category
import com.andreypmi.core_domain.models.CategoryWithWordCount
import com.andreypmi.core_domain.models.Word
import com.andreypmi.dictionaryforwords.data.storage.dto.CategoryWithWordCountModel
import com.andreypmi.dictionaryforwords.data.storage.entites.CategoriesEntity
import com.andreypmi.dictionaryforwords.data.storage.entites.WordsEntity

object EntityMapper {
    fun toDomainModel(entity: WordsEntity): Word {
        return Word(
            id = entity.id,
            idCategory = entity.idCategory,
            word = entity.word,
            translate = entity.translate,
            description = entity.description ?: ""
        )
    }
    fun toDomainModel(entity: CategoriesEntity): Category {
        return Category(
            id = entity.id,
            category = entity.categoryName
        )
    }
    fun toDomainModel(model: CategoryWithWordCountModel): CategoryWithWordCount {
        return CategoryWithWordCount(
            category = Category(
                id = model.id,
                category = model.categoryName
            ),
            wordCount = model.wordCount
        )
    }
    fun fromDomainModel(model: Word):WordsEntity{
        return WordsEntity(
            id = model.id?:"",
            idCategory = model.idCategory,
            word = model.word,
            translate = model.translate,
            description = model.description
        )
    }
    fun fromDomainModel(model: Category):CategoriesEntity{
        return CategoriesEntity(
            id = model.id,
            categoryName = model.category
        )
    }
}