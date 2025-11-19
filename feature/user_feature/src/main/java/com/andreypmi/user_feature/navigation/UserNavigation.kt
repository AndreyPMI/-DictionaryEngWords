package com.andreypmi.user_feature.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.andreypmi.navigation_api.BottomNavigationController
import com.andreypmi.user_feature.userScreen.UserScreen

fun NavGraphBuilder.user(
    bottomNavigationController: BottomNavigationController? = null
) {
    composable(route = UserDestination.route) {
        UserScreen(bottomNavigationController = bottomNavigationController)
    }
}
