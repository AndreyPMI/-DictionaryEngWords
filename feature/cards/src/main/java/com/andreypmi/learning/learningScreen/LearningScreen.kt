package com.andreypmi.learning.learningScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.andreypmi.learning.categoryScreen.CategoriesScreen
import com.andreypmi.learning.categoryScreen.viewModel.CategoriesIntent
import com.andreypmi.learning.categoryScreen.viewModel.CategoriesViewModel
import com.andreypmi.learning.di.DaggerLearningComponent
import com.andreypmi.learning.di.LearningDepsProvider
import com.andreypmi.learning.learningScreen.sessionResultScreen.SessionResultScreen
import com.andreypmi.learning.learningScreen.viewModels.LearningSessionIntent
import com.andreypmi.learning.learningScreen.viewModels.LearningSessionViewModel
import com.andreypmi.learning.learningScreen.сardStackScreen.CardStackScreen
import com.andreypmi.learning.navigation.CardStackDestination
import com.andreypmi.learning.navigation.CategoriesDestination
import com.andreypmi.learning.navigation.LearningDestination
import com.andreypmi.learning.navigation.SessionResultDestination
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
        route = LearningDestination.route
    ) {
        composable(CategoriesDestination.route) {
            LaunchedEffect(Unit) {
                bottomNavigationController?.show()
            }
            val categoriesViewModel: CategoriesViewModel = viewModel(
                factory = learningComponent.vmCategoryFactory
            )
            LaunchedEffect(Unit) {
                categoriesViewModel.processIntent(CategoriesIntent.LoadCategories)
            }
            val categoriesState = categoriesViewModel.state.collectAsState().value
            CategoriesScreen(
                onCategoryClick = { categoryId ->
                    sessionViewModel.processIntent(LearningSessionIntent.StartSession(categoryId))
                    categoriesViewModel.processIntent(CategoriesIntent.CategoryClicked(categoryId))
                    innerNavController.navigate(CardStackDestination.createRoute(categoryId))
                },
                categoriesState = categoriesState
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
            val sessionState by sessionViewModel.sessionState.collectAsState()
            CardStackScreen(
                onSessionCompleted = {
                    innerNavController.navigate(SessionResultDestination.route)
                },
                onBack = {
                    sessionViewModel.processIntent(LearningSessionIntent.ResetSession)
                    innerNavController.popBackStack()
                },
                sessionState = sessionState,
                onSwipeLeft = { word ->
                    sessionViewModel.processIntent(
                        LearningSessionIntent.WordSwiped(
                            isKnown = false,
                            word
                        )
                    )
                },
                onSwipeRight = { word ->
                    sessionViewModel.processIntent(
                        LearningSessionIntent.WordSwiped(
                            isKnown = true,
                            word
                        )
                    )
                },
                onCardFlip = { cardId ->
                    sessionViewModel.processIntent(
                        LearningSessionIntent.CardFlipped(
                            cardId
                        )
                    )
                },
            )
        }

        composable(SessionResultDestination.route) {
            LaunchedEffect(Unit) {
                bottomNavigationController?.show()
            }
            val sessionResult = sessionViewModel.sessionResult.collectAsState().value
            SessionResultScreen(
                sessionResult = sessionResult,
                onRetry = {
                    sessionViewModel.processIntent(LearningSessionIntent.RetryWithDifficultWords)
                    innerNavController.popBackStack()
                },
                onFinish = {
                    innerNavController.popBackStack(CategoriesDestination.route, false)
                }
            )
        }
    }
}