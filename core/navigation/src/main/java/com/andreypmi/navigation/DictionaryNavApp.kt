package com.andreypmi.navigation

import androidx.compose.runtime.Composable
import com.andreypmi.dictionaryforwords.core.ui.theme.DictionaryForWordsTheme
import com.andreypmi.learning.LearningTopLevelDestination
import com.andreypmi.dictionaryforwords.word_list.presentation.profile.ProfileTopLevelDestination
import com.andreypmi.dictionaryforwords.word_list.presentation.words.WordsTopLevelDestination

@Composable
fun DictionaryNavApp(
) {
    DictionaryForWordsTheme {
        DictionaryNavHost(destinations = listOf(
            WordsTopLevelDestination,
            com.andreypmi.learning.LearningTopLevelDestination, ProfileTopLevelDestination
        ))
    }
}