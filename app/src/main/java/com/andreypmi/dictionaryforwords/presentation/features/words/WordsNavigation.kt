package com.andreypmi.dictionaryforwords.presentation.features.words

import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.andreypmi.dictionaryforwords.core.navigation.NavDestination

fun NavGraphBuilder.words() {
    composable(route = WordsDestination.route) {
        val viewModel: WordsViewModel = viewModel()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        MainScreen(uiState = uiState)
    }
}

object WordsDestination : NavDestination {
    override val route = "words"
}