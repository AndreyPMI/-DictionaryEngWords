package com.andreypmi.learning.navigation

object CategoriesDestination {
    const val route = "categories"
}
object CardStackDestination {
    const val route = "card_stack"
    const val categoryIdArg = "categoryId"
    val routeWithArgs = "$route/{$categoryIdArg}"
    fun createRoute(categoryId: Int): String {
        return "$route/$categoryId"
    }
}
object SessionResultDestination {
    const val route = "session_result"
    const val categoryIdArg = "categoryId"
    const val difficultWordIdsArg = "difficultWordIds"
    const val allWordIdsArg = "allWordIds"
    val routeWithArgs = "$route/{$categoryIdArg}?$difficultWordIdsArg={$difficultWordIdsArg}&$allWordIdsArg={$allWordIdsArg}"
    fun createRoute(categoryId: Int, difficultWordIds: List<String>, allWordIds: List<String>): String {
        val difficultIdsString = difficultWordIds.joinToString(",")
        val allIdsString = allWordIds.joinToString(",")
        return "$route/$categoryId?$difficultWordIdsArg=$difficultIdsString&$allWordIdsArg=$allIdsString"
    }
}
