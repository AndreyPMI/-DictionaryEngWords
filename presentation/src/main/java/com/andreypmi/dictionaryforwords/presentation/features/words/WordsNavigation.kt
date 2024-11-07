package com.andreypmi.dictionaryforwords.presentation.features.words

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.andreypmi.dictionaryforwords.core.navigation.NavDestination

fun NavGraphBuilder.words() {
    composable(route = WordsDestination.route) {
        MainScreen()
    }
}

object WordsDestination : NavDestination {
    override val route = "words"
}