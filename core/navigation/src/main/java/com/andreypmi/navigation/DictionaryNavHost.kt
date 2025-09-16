package com.andreypmi.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.andreypmi.learning.learning
import com.andreypmi.dictionaryforwords.word_list.presentation.profile.profile
import com.andreypmi.dictionaryforwords.word_list.presentation.words.WordsDestination
import com.andreypmi.dictionaryforwords.word_list.presentation.words.words
import com.andreypmi.navigation_api.DictionaryNavBarDestination
import com.andreypmi.navigation_api.navigateSingleTopTo

@Composable
fun DictionaryNavHost(
    navController: NavHostController = rememberNavController(),
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
                words()
                learning()
                profile()
            }
        }
    }
}

