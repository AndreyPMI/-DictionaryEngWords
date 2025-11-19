package com.andreypmi.dictionaryforwords.word_list.presentation.words

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.andreypmi.dictionaryforwords.core.ui.R
import com.andreypmi.dictionaryforwords.word_list.di.DaggerWordListComponent
import com.andreypmi.dictionaryforwords.word_list.di.WordListDepsProvider
import com.andreypmi.dictionaryforwords.word_list.presentation.category.CategoryPanel
import com.andreypmi.dictionaryforwords.word_list.presentation.category.viewModels.CategoryViewModel
import com.andreypmi.dictionaryforwords.word_list.presentation.words.viewModels.WordsViewModel
import com.andreypmi.navigation_api.DictionaryNavBarDestination
import com.andreypmi.navigation_api.DictionaryNavDestination

fun NavGraphBuilder.words(
) {
    composable(route = WordsDestination.route) {
        val wordsComponent =
            DaggerWordListComponent.builder().addDeps(WordListDepsProvider.deps).build()
        val wordsViewModel = viewModel<WordsViewModel>(factory = wordsComponent.vmWordsFactory)
        val categoryViewModel = viewModel<CategoryViewModel>(factory = wordsComponent.vmCategoryFactory)
        CategoryPanel(wordsViewModel,categoryViewModel)
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