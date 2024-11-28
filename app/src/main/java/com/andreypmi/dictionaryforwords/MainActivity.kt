package com.andreypmi.dictionaryforwords

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.andreypmi.dictionaryforwords.core.ui.theme.DictionaryForWordsTheme
import com.andreypmi.dictionaryforwords.presentation.DictionaryNavHost
import com.andreypmi.dictionaryforwords.presentation.features.learning.LearningTopLevelDestination
import com.andreypmi.dictionaryforwords.presentation.features.profile.ProfileTopLevelDestination
import com.andreypmi.dictionaryforwords.presentation.features.words.IWordsViewModel
import com.andreypmi.dictionaryforwords.presentation.features.words.WordsTopLevelDestination
import com.andreypmi.dictionaryforwords.presentation.features.words.WordsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel by viewModel<WordsViewModel>()

        setContent {
            DictionaryNavApp(viewModel)
        }
    }
}

@Composable
fun DictionaryNavApp(
    wordsViewModel : IWordsViewModel
) {
    DictionaryForWordsTheme {
        DictionaryNavHost(wordsViewModel = wordsViewModel, destinations = listOf(
            WordsTopLevelDestination, LearningTopLevelDestination,ProfileTopLevelDestination
        ))
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DictionaryForWordsTheme {

    }
}