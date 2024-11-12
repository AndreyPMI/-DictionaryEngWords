package com.andreypmi.dictionaryforwords.presentation.features.words

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.andreypmi.dictionaryforwords.presentation.navigation.DictionaryNavDestination

fun NavGraphBuilder.words() {
    composable(route = WordsDestination.route) {
        MainScreen()
    }
}

object WordsDestination : DictionaryNavDestination {
    override val route = "words"
}