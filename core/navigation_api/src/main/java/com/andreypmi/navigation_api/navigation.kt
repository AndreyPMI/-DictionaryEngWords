package com.andreypmi.navigation_api

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.navigation.NavHostController

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