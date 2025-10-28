package com.andreypmi.user_feature.userScreen.nested_screens.shared_group.models

import com.andreypmi.core_domain.models.CategoryWithWordCount

data class ShareGroupCategory(
    val id: String,
    val name: String,
    val wordCount: Int
)
fun CategoryWithWordCount.toPresenterModel():ShareGroupCategory{
    return ShareGroupCategory(
        id = this.category.id,
        name = this.category.category,
        wordCount = this.wordCount
    )
}