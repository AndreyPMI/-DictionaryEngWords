package com.andreypmi.dictionaryforwords.word_list.presentation.category

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.andreypmi.dictionaryforwords.core.ui.R
import com.andreypmi.navigation_api.DictionaryNavBarDestination
import com.andreypmi.navigation_api.DictionaryNavDestination

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