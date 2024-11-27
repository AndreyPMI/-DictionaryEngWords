package com.andreypmi.dictionaryforwords.presentation.features.words

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.andreypmi.dictionaryforwords.presentation.navigation.DictionaryNavDestination
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.getScopeId

fun NavGraphBuilder.words(
     viewModel: IWordsViewModel
) {
    composable(route = WordsDestination.route) {
        MainScreen(viewModel)
    }
}

object WordsDestination : DictionaryNavDestination {
    override val route = "words"
}