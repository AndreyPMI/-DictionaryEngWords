package com.andreypmi.learning.navigation

import com.andreypmi.core_domain.models.Word

object CategoriesDestination {
    const val route = "categories"
}
object CardStackDestination {
    const val route = "card_stack"
    const val categoryIdArg = "categoryId"
    val routeWithArgs = "$route/{$categoryIdArg}"
    fun createRoute(categoryId: String): String {
        return "$route/$categoryId"
    }
}
object SessionResultDestination {
    const val route = "session_result"
    const val categoryIdArg = "categoryId"
    const val difficultWordIdsArg = "difficultWordIds"
    const val allWordIdsArg = "allWordIds"
    val routeWithArgs = "$route/{$categoryIdArg}?$difficultWordIdsArg={$difficultWordIdsArg}&$allWordIdsArg={$allWordIdsArg}"
    fun createRoute(categoryId: String, difficultWords: List<Word>, allWords: List<Word>): String {
        val difficultIdsString = difficultWords.joinToString(",") { it.id.toString() }
        val allIdsString = allWords.joinToString(",") { it.id.toString() }
        return "$route/$categoryId?$difficultWordIdsArg=$difficultIdsString&$allWordIdsArg=$allIdsString"
    }
}
