package com.andreypmi.dictionaryforwords.word_list.presentation.words

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
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
    composable(
        route = "${NotificationStubDestination.route}?wordId={wordId}&type={notificationType}",
        deepLinks = listOf(
            navDeepLink {
                uriPattern = NotificationStubDestination.deepLinkRoute
            }
        )
    ) { backStackEntry ->
        val wordId = backStackEntry.arguments?.getString("wordId")
        val notificationType = backStackEntry.arguments?.getString("notificationType")

        NotificationStubScreen(
            wordId = wordId,
            notificationType = notificationType,
            onBack = {
               // backStackEntry.savedStateHandle()
            }
        )
    }

}

object WordsDestination : DictionaryNavDestination {
    override val route = "words"
}
object NotificationStubDestination : DictionaryNavDestination {
    override val route = "notification_stub"

    val deepLinkRoute = "dictionaryforwords://notification"

    fun createRoute(wordId: String? = null, notificationType: String? = null): String {
        return "notification_stub?wordId=$wordId&type=$notificationType"
    }
}
data object WordsTopLevelDestination : DictionaryNavBarDestination {
    override val iconId = R.drawable.ic_book
    override val titleId = R.string.words
    override val route = WordsDestination.route
}