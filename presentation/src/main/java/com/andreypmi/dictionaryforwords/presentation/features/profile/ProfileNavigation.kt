package com.andreypmi.dictionaryforwords.presentation.features.profile

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.andreypmi.dictionaryforwords.presentation.features.words.IWordsViewModel
import com.andreypmi.dictionaryforwords.presentation.features.words.MainScreen
import com.andreypmi.dictionaryforwords.presentation.navigation.DictionaryNavDestination

fun NavGraphBuilder.profile(
) {
    composable(route = ProfileDestination.route) {
       // ProfileScreen()
    }
}

object ProfileDestination : DictionaryNavDestination {
    override val route = "profile"
}