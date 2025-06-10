package com.andreypmi.dictionaryforwords.word_list.presentation.profile

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.andreypmi.dictionaryforwords.core.ui.R
import com.andreypmi.navigation_api.DictionaryNavBarDestination
import com.andreypmi.navigation_api.DictionaryNavDestination

fun NavGraphBuilder.profile(
) {
    composable(route = ProfileDestination.route) {
       ProfileScreen()
    }
}

object ProfileDestination : DictionaryNavDestination {
    override val route = "profile"
}

data object ProfileTopLevelDestination : DictionaryNavBarDestination {
    override val iconId = R.drawable.ic_person
    override val titleId = R.string.profile
    override val route = ProfileDestination.route
}