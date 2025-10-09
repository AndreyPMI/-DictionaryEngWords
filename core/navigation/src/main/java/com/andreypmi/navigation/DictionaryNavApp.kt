package com.andreypmi.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import com.andreypmi.dictionaryforwords.core.ui.theme.DictionaryForWordsTheme
import com.andreypmi.dictionaryforwords.word_list.presentation.profile.ProfileTopLevelDestination
import com.andreypmi.dictionaryforwords.word_list.presentation.words.WordsTopLevelDestination
import com.andreypmi.learning.navigation.LearningTopLevelDestination

@Composable
fun DictionaryNavApp(
) {
    val bottomNavState = remember { BottomNavigationState() }
    DictionaryForWordsTheme {
        CompositionLocalProvider(
            LocalBottomNavigationState provides bottomNavState
        ) {
            DictionaryNavHost(
                destinations = listOf(
                    WordsTopLevelDestination,
                    LearningTopLevelDestination,
                    ProfileTopLevelDestination
                )
            )
        }
    }
}