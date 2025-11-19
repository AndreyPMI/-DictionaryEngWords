package com.andreypmi.dictionaryforwords.word_list.presentation.words

import android.util.Log
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.andreypmi.dictionaryforwords.core.ui.R
import com.andreypmi.dictionaryforwords.word_list.di.DaggerWordListComponent
import com.andreypmi.dictionaryforwords.word_list.di.WordListDepsProvider
import com.andreypmi.dictionaryforwords.word_list.presentation.words.viewModels.WordsViewModel
import com.andreypmi.navigation_api.DictionaryNavBarDestination
import com.andreypmi.navigation_api.DictionaryNavDestination

fun NavGraphBuilder.words(
) {
    composable(route = WordsDestination.route) {
        Log.d("AAA", WordListDepsProvider.toString())
        val wordsComponent =
            DaggerWordListComponent.builder().addDeps(WordListDepsProvider.deps).build()
        val viewModel = viewModel<WordsViewModel>(factory = wordsComponent.vmFactory)
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