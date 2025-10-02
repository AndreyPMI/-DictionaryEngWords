package com.andreypmi.learning.learningScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.andreypmi.learning.categoryScreen.CategoriesScreen
import com.andreypmi.learning.categoryScreen.viewModel.CategoriesViewModel
import com.andreypmi.learning.di.DaggerLearningComponent
import com.andreypmi.learning.di.LearningDepsProvider
import com.andreypmi.learning.navigation.CardStackDestination
import com.andreypmi.learning.navigation.CategoriesDestination
import com.andreypmi.learning.navigation.SessionResultDestination
import com.andreypmi.learning.learningScreen.sessionResultScreen.SessionResultScreen
import com.andreypmi.learning.learningScreen.viewModels.LearningSessionViewModel
import com.andreypmi.learning.learningScreen.сardStackScreen.CardStackScreen
import com.andreypmi.navigation_api.BottomNavigationController

@Composable
fun LearningScreen(
    bottomNavigationController: BottomNavigationController? = null
) {
    val innerNavController = rememberNavController()
    val learningComponent = DaggerLearningComponent.factory().create(LearningDepsProvider.deps)

    val sessionViewModel: LearningSessionViewModel = viewModel(
        factory = learningComponent.vmLearningSessionFactory
    )

    NavHost(
        navController = innerNavController,
        startDestination = CategoriesDestination.route,
        route = "learning_route"
    ) {
        composable(CategoriesDestination.route) {
            LaunchedEffect(Unit) {
                bottomNavigationController?.show()
            }

            val categoriesViewModel: CategoriesViewModel = viewModel(
                factory = learningComponent.vmCategoryFactory
            )

            CategoriesScreen(
                viewModel = categoriesViewModel,
                onCategoryClick = { categoryId ->
                    sessionViewModel.startSession(categoryId)
                    innerNavController.navigate(CardStackDestination.createRoute(categoryId))
                }
            )
        }

        composable(
            route = CardStackDestination.routeWithArgs,
            arguments = listOf(
                navArgument(CardStackDestination.categoryIdArg) {
                    type = NavType.IntType
                }
            )
        ) { navBackStackEntry ->
            LaunchedEffect(Unit) {
                bottomNavigationController?.hide()
            }
            DisposableEffect(Unit) {
                onDispose {
                    bottomNavigationController?.show()
                }
            }

            CardStackScreen(
                viewModel = sessionViewModel,
                onSessionCompleted = {
                    innerNavController.navigate(SessionResultDestination.route)
                },
                onBack = {
                    sessionViewModel.resetSession()
                    innerNavController.popBackStack()
                }
            )
        }

        composable(SessionResultDestination.route) {
            LaunchedEffect(Unit) {
                bottomNavigationController?.show()
            }

            SessionResultScreen(
                viewModel = sessionViewModel,
                onRetry = {
                    sessionViewModel.resetSession()
                    innerNavController.popBackStack()
                },
                onFinish = {
                    sessionViewModel.resetSession()
                    innerNavController.popBackStack(CategoriesDestination.route, false)
                }
            )
        }
    }
}