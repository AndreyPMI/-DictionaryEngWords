package com.andreypmi.dictionaryforwords.presentation.features.words

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.andreypmi.dictionaryforwords.core.ui.R
import com.andreypmi.dictionaryforwords.presentation.navigation.DictionaryNavBarDestination
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

data object WordsTopLevelDestination : DictionaryNavBarDestination {
    override val iconId = R.drawable.ic_book
    override val titleId = R.string.words
    override val route = WordsDestination.route
}