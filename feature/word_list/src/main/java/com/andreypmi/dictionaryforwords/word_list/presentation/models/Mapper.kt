package com.andreypmi.dictionaryforwords.word_list.presentation.models

import com.andreypmi.core_domain.models.Word

object Mapper {
    fun fromDomainModel(word: Word): WordState{
        return WordState(
            id = word.id,
            idCategory = word.idCategory,
            word = word.word,
            translation = word.translate,
            description = word.description,
        )
    }
    fun toDomainModel(wordState: WordState): Word {
        return Word(
            id = wordState.id,
            idCategory = wordState.idCategory,
            word = wordState.word,
            translate = wordState.translation,
            description = wordState.description,
        )

    }
}