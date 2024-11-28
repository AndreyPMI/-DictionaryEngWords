package com.andreypmi.dictionaryforwords.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.andreypmi.dictionaryforwords.presentation.features.learning.learning
import com.andreypmi.dictionaryforwords.presentation.features.profile.profile
import com.andreypmi.dictionaryforwords.presentation.features.words.IWordsViewModel
import com.andreypmi.dictionaryforwords.presentation.features.words.WordsDestination
import com.andreypmi.dictionaryforwords.presentation.features.words.words
import com.andreypmi.dictionaryforwords.presentation.navigation.DictionaryNavBarDestination
import com.andreypmi.dictionaryforwords.presentation.navigation.navigateSingleTopTo
import com.andreypmi.dictionaryforwords.presentation.widgets.NavBar

@Composable
fun DictionaryNavHost(
    navController: NavHostController = rememberNavController(),
    wordsViewModel: IWordsViewModel,
    destinations: List<DictionaryNavBarDestination>,
) {
    Scaffold(
        bottomBar = {
            NavBar(
                currentDestination = null,
                destinations = destinations,
                onNavigateToTopLevel = { it ->
                    navController.navigateSingleTopTo(it)
                })
        }

    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {

            NavHost(
                navController = navController,
                startDestination = WordsDestination.route
            ) {
                words(wordsViewModel)
                learning()
                profile()
            }
        }
    }
}

