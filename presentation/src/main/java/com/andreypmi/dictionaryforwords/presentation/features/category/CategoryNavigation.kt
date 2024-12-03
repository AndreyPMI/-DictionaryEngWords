package com.andreypmi.dictionaryforwords.presentation.features.category

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.andreypmi.dictionaryforwords.core.ui.R
import com.andreypmi.dictionaryforwords.presentation.features.learning.LearningScreen
import com.andreypmi.dictionaryforwords.presentation.navigation.DictionaryNavBarDestination
import com.andreypmi.dictionaryforwords.presentation.navigation.DictionaryNavDestination

fun NavGraphBuilder.category(
) {
    composable(route = CategoryDestination.route) {
        //CategoryScreen()
    }
}

object CategoryDestination : DictionaryNavDestination {
    override val route = "learning"
}

data object CategoryTopLevelDestination : DictionaryNavBarDestination {
    override val iconId = R.drawable.ic_book
    override val titleId = R.string.words
    override val route = CategoryDestination.route
}