package com.andreypmi.dictionaryforwords.data.api.mapper

import com.andreypmi.core_domain.models.SharedCategory
import com.andreypmi.core_domain.models.Word
import com.andreypmi.dictionaryforwords.data.api.models.NetworkSharedCategory
import com.andreypmi.dictionaryforwords.data.api.models.NetworkSharedWord
import kotlin.time.Duration.Companion.hours

object SharedCategoryMapper {

    fun domainToNetwork(domain: SharedCategory): NetworkSharedCategory {
        return NetworkSharedCategory(
            id = domain.id,
            categoryName = domain.categoryName,
            words = domain.words.map { word ->
                NetworkSharedWord(
                    original = word.word,
                    translation = word.translate,
                    description = word.description
                )
            },
            createdAt = System.currentTimeMillis(),
            expiresAt = System.currentTimeMillis() + 24.hours.inWholeMilliseconds
        )
    }

    fun networkToDomain(network: NetworkSharedCategory): SharedCategory {
        return SharedCategory(
            id = network.id,
            categoryName = network.categoryName,
            words = network.words.map { networkWord ->
                Word(
                    id = null,
                    idCategory = "",
                    word = networkWord.original,
                    translate = networkWord.translation,
                    description = networkWord.description?:""
                )
            }
        )
    }
}