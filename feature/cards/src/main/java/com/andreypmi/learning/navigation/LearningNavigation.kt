package com.andreypmi.learning.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.andreypmi.dictionaryforwords.core.ui.R
import com.andreypmi.learning.learningScreen.LearningScreen
import com.andreypmi.navigation_api.BottomNavigationController
import com.andreypmi.navigation_api.DictionaryNavBarDestination
import com.andreypmi.navigation_api.DictionaryNavDestination

fun NavGraphBuilder.learning(
    bottomNavigationController: BottomNavigationController? = null
) {
    composable(route = LearningDestination.route) {
        LearningScreen(bottomNavigationController = bottomNavigationController)
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