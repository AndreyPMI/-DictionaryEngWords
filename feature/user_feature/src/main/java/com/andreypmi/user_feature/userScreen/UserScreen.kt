package com.andreypmi.user_feature.userScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.andreypmi.navigation_api.BottomNavigationController
import com.andreypmi.user_feature.di.DaggerUserComponent
import com.andreypmi.user_feature.di.UserDepsProvider
import com.andreypmi.user_feature.navigation.LoadGroupDestination
import com.andreypmi.user_feature.navigation.NotificationsDestination
import com.andreypmi.user_feature.navigation.QRCodeDestination
import com.andreypmi.user_feature.navigation.SettingsDestination
import com.andreypmi.user_feature.navigation.ShareGroupDestination
import com.andreypmi.user_feature.navigation.UserDestination
import com.andreypmi.user_feature.navigation.UserMainDestination
import com.andreypmi.user_feature.userScreen.nested_screens.NotificationsScreen
import com.andreypmi.user_feature.userScreen.nested_screens.QRCodeScreen
import com.andreypmi.user_feature.userScreen.nested_screens.SettingsScreen
import com.andreypmi.user_feature.userScreen.nested_screens.ShareGroupScreen
import com.andreypmi.user_feature.userScreen.nested_screens.UserMainScreen

@Composable
fun UserScreen(
    bottomNavigationController: BottomNavigationController? = null
) {
    val innerNavController = rememberNavController()
    val userComponent = DaggerUserComponent.factory().create(UserDepsProvider.deps)

    NavHost(
        navController = innerNavController,
        startDestination = UserMainDestination.route,
        route = UserDestination.route
    ) {
        composable(UserMainDestination.route) {
            LaunchedEffect(Unit) {
                bottomNavigationController?.show()
            }

            UserMainScreen(
                onShareGroupClick = {
                    innerNavController.navigate(ShareGroupDestination.route)
                },
                onLoadGroupClick = {
                    innerNavController.navigate(LoadGroupDestination.route)
                },
                onNotificationsClick = {
                    innerNavController.navigate(NotificationsDestination.route)
                },
                onSettingsClick = {
                    innerNavController.navigate(SettingsDestination.route)
                }
            )
        }

        composable(ShareGroupDestination.route) {
            LaunchedEffect(Unit) {
                bottomNavigationController?.hide()
            }
//            val shareGroupViewModel: ShareGroupViewModel = viewModel(
//                factory = userComponent.vmShareGroupFactory
//            )

            LaunchedEffect(Unit) {
               // shareGroupViewModel.processIntent(ShareGroupIntent.LoadGroups)
            }

                //val shareGroupState = shareGroupViewModel.state.collectAsState().value
//            ShareGroupScreen(
//                groupsState = shareGroupState,
//                onGroupSelected = { groupId ->
//                    innerNavController.navigate(QRCodeDestination.createRoute(groupId))
//                },
//                onBack = {
//                    innerNavController.popBackStack()
//                }
//            )
        }

        composable(
            route = QRCodeDestination.routeWithArgs,
            arguments = listOf(
                navArgument(QRCodeDestination.groupIdArg) {
                    type = NavType.IntType
                }
            )
        ) { navBackStackEntry ->
            val groupId = navBackStackEntry.arguments?.getInt(QRCodeDestination.groupIdArg) ?: 0
//            val qrCodeViewModel: QRCodeViewModel = viewModel(
//                factory = userComponent.vmQRCodeFactory
//            )

            LaunchedEffect(groupId) {
               // qrCodeViewModel.processIntent(QRCodeIntent.GenerateQRCode(groupId))
            }

            //val qrState = qrCodeViewModel.state.collectAsState().value
//            QRCodeScreen(
//                qrState = qrState,
//                onBack = {
//                    innerNavController.popBackStack()
//                },
//                onShare = { qrBitmap ->
//                    // Логика шаринга QR кода
//                    qrCodeViewModel.processIntent(QRCodeIntent.ShareQRCode(qrBitmap))
//                }
//            )
        }

        composable(LoadGroupDestination.route) {
            LaunchedEffect(Unit) {
                bottomNavigationController?.hide()
            }
//            val loadGroupViewModel: LoadGroupViewModel = viewModel(
//                factory = userComponent.vmLoadGroupFactory
//            )

//            LoadGroupScreen(
//                onQRCodeScanned = { qrData ->
//                    //loadGroupViewModel.processIntent(LoadGroupIntent.ProcessQRCode(qrData))
//                },
//                onBack = {
//                    innerNavController.popBackStack()
//                },
//                loadingState = loadGroupViewModel.state.collectAsState().value
//            )
        }

        composable(NotificationsDestination.route) {
            NotificationsScreen(
                onBack = { innerNavController.popBackStack() }
            )
        }

        composable(SettingsDestination.route) {
            SettingsScreen(
                onBack = { innerNavController.popBackStack() }
            )
        }
    }
}