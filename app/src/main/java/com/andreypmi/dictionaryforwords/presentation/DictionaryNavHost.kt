package com.andreypmi.dictionaryforwords.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.andreypmi.dictionaryforwords.presentation.features.words.WordsDestination

@Composable
internal fun DictionaryNavHost(
    navController: NavHostController = rememberNavController()
){
    NavHost(
        navController = navController,
        startDestination = WordsDestination.route
    ){

    }
}

