package com.andreypmi.dictionaryforwords.presentation.features.learning

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.andreypmi.dictionaryforwords.presentation.navigation.DictionaryNavDestination

fun NavGraphBuilder.learning(
) {
    composable(route = LearningDestination.route) {
        // LearningScreen()
    }
}

object LearningDestination : DictionaryNavDestination {
    override val route = "learning"
}