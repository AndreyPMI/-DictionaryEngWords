package com.andreypmi.dictionaryforwords.presentation.features.learning

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.andreypmi.dictionaryforwords.presentation.navigation.DictionaryNavDestination
import com.andreypmi.dictionaryforwords.core.ui.R
import com.andreypmi.dictionaryforwords.presentation.navigation.DictionaryNavBarDestination

fun NavGraphBuilder.learning(
) {
    composable(route = LearningDestination.route) {
        LearningScreen()
    }
}

object LearningDestination : DictionaryNavDestination {
    override val route = "learning"
}

data object LearningTopLevelDestination : DictionaryNavBarDestination {
    override val iconId = R.drawable.ic_card
    override val titleId = R.string.learning
    override val route = LearningDestination.route
}