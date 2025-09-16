package com.andreypmi.learning

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.andreypmi.core_domain.di.DaggerDomainComponent
import com.andreypmi.learning.categoryScreen.CategoriesScreen
import com.andreypmi.learning.categoryScreen.viewModel.CategoriesViewModel
import com.andreypmi.learning.categoryScreen.viewModel.CategoriesViewModelFactory
import com.andreypmi.learning.di.DaggerLearningComponent
import com.andreypmi.learning.di.LearningDeps
import com.andreypmi.learning.di.LearningDepsProvider
import com.andreypmi.learning.navigation.CardStackDestination
import com.andreypmi.learning.navigation.CategoriesDestination
import com.andreypmi.learning.navigation.SessionResultDestination
import com.andreypmi.learning.sessionResultScreen.SessionResultScreen
import com.andreypmi.learning.сardStackScreen.CardStackScreen

@Composable
fun LearningScreen() {
    val innerNavController = rememberNavController()
    val learningComponent = DaggerLearningComponent.factory().create(LearningDepsProvider.deps)
    NavHost(
        navController = innerNavController,
        startDestination = CategoriesDestination.route,
        route = "learning_route"
    ) {
        composable(CategoriesDestination.route) {
            val vmCategory: CategoriesViewModel = viewModel(factory = learningComponent.vmCategoryFactory)
            CategoriesScreen(
                viewModel = vmCategory,
                onCategoryClick = { categoryId ->
                    innerNavController.navigate(
                        CardStackDestination.createRoute(categoryId)
                    )
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
            val categoryId = navBackStackEntry.arguments?.getString(CardStackDestination.categoryIdArg) ?: ""
            CardStackScreen(
                categoryId = categoryId,
                onSessionFinished = { result ->
                    // Навигация на экран результатов
          //          val difficultIdsString = result.difficultWords.joinToString(",") { it.id }
           //         val allIdsString = result.allWords.joinToString(",") { it.id }

                    innerNavController.navigate(
                        SessionResultDestination.routeWithArgs
                    //        .replace("{${SessionResultDestination.categoryIdArg}}", result.categoryId)
                     //       .replace("{${SessionResultDestination.difficultWordIdsArg}}", difficultIdsString)
                    //        .replace("{${SessionResultDestination.allWordIdsArg}}", allIdsString)
                    )
                },
                onBack = { innerNavController.popBackStack() }
            )
        }
        composable(
            route = SessionResultDestination.routeWithArgs,
            arguments = listOf(
                navArgument(SessionResultDestination.categoryIdArg) { type = NavType.StringType },
                navArgument(SessionResultDestination.difficultWordIdsArg) { type = NavType.StringType },
                navArgument(SessionResultDestination.allWordIdsArg) { type = NavType.StringType }
            )
        ) { navBackStackEntry ->
            SessionResultScreen(
                onRetry = { innerNavController.popBackStack() },
                onFinish = {
                    innerNavController.popBackStack(
                        CategoriesDestination.route,
                        inclusive = false
                    )
                },
                categoryId = TODO(),
                difficultWordIds = TODO(),
                allWordIds = TODO()
            )
        }
    }
}