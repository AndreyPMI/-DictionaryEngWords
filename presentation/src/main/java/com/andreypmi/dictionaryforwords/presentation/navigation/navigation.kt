package com.andreypmi.dictionaryforwords.presentation.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import okhttp3.Route

interface DictionaryNavDestination {
    val route: String
}

interface DictionaryNavBarDestination : DictionaryNavDestination {
    @get:DrawableRes
    val iconId: Int

    @get:StringRes
    val titleId: Int
}

fun NavHostController.navigateSingleTopTo(route: String) {
    navigate(route)
}