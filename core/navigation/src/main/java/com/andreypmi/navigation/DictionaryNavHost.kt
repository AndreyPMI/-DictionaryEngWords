package com.andreypmi.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.andreypmi.learning.navigation.learning
import com.andreypmi.dictionaryforwords.word_list.presentation.words.WordsDestination
import com.andreypmi.dictionaryforwords.word_list.presentation.words.words
import com.andreypmi.navigation_api.DictionaryNavBarDestination
import com.andreypmi.navigation_api.navigateSingleTopTo
import com.andreypmi.user_feature.navigation.user

@Composable
fun DictionaryNavHost(
    navController: NavHostController = rememberNavController(),
    destinations: List<DictionaryNavBarDestination>,
) {
    val bottomNavState = LocalBottomNavigationState.current
    Scaffold(
        bottomBar = {
            if (bottomNavState.isVisible) {
                NavBar(
                    currentDestination = null,
                    destinations = destinations,
                    onNavigateToTopLevel = { it ->
                        navController.navigateSingleTopTo(it)
                    }
                )
            }
        }

    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(
                navController = navController,
                startDestination = WordsDestination.route
            ) {
                words()
                learning(bottomNavState)
                user()
            }
        }
    }
}

