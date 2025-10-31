package com.andreypmi.user_feature.userScreen

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.RectF
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.andreypmi.core_domain.models.QrCodeData
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
import com.andreypmi.user_feature.userScreen.nested_screens.SettingsScreen
import com.andreypmi.user_feature.userScreen.nested_screens.UserMainScreen
import com.andreypmi.user_feature.userScreen.nested_screens.qrCodeScreen.QRCodeScreen
import com.andreypmi.user_feature.userScreen.nested_screens.qrCodeScreen.viewModels.QRCodeIntent
import com.andreypmi.user_feature.userScreen.nested_screens.qrCodeScreen.viewModels.QRCodeViewModel
import com.andreypmi.user_feature.userScreen.nested_screens.shared_group.ShareGroupScreen
import com.andreypmi.user_feature.userScreen.nested_screens.shared_group.viewmodels.ShareGroupIntent
import com.andreypmi.user_feature.userScreen.nested_screens.shared_group.viewmodels.ShareGroupViewModel
import java.io.File
import java.io.FileOutputStream
import androidx.core.graphics.createBitmap
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.andreypmi.user_feature.userScreen.nested_screens.loadWords.LoadGroupScreen
import com.andreypmi.user_feature.userScreen.nested_screens.loadWords.models.LoadGroupState
import com.andreypmi.user_feature.userScreen.nested_screens.loadWords.viewModels.LoadGroupIntent
import com.andreypmi.user_feature.userScreen.nested_screens.loadWords.viewModels.LoadGroupViewModel

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
            val shareGroupViewModel: ShareGroupViewModel = viewModel(
                factory = userComponent.vmShareGroupFactory
            )

            LaunchedEffect(Unit) {
                shareGroupViewModel.processIntent(ShareGroupIntent.LoadFirstPage)
            }

            val shareGroupState = shareGroupViewModel.state.collectAsState().value
            ShareGroupScreen(
                state = shareGroupState,
                onIntent = { intent -> shareGroupViewModel.processIntent(intent) },
                onNavigateToQR = { categoryId ->
                    innerNavController.navigate(
                        QRCodeDestination.createRoute(
                            categoryId
                        )
                    )
                },
                onBack = { innerNavController.popBackStack() }
            )
        }

        composable(
            route = QRCodeDestination.routeWithArgs,
            arguments = listOf(
                navArgument(QRCodeDestination.categoryIdArg) {
                    type = NavType.StringType
                }
            )
        ) { navBackStackEntry ->
            LaunchedEffect(Unit) {
                bottomNavigationController?.hide()
            }

            val categoryId = navBackStackEntry.arguments?.getString(QRCodeDestination.categoryIdArg) ?: ""
            val qrCodeViewModel: QRCodeViewModel = viewModel(
                factory = userComponent.vmQRCodeFactory
            )

            LaunchedEffect(categoryId) {
                qrCodeViewModel.processIntent(QRCodeIntent.GenerateQRCode(categoryId))
            }

            val qrState = qrCodeViewModel.state.collectAsState().value
            val context = LocalContext.current
            QRCodeScreen(
                qrState = qrState,
                onBack = { innerNavController.popBackStack() },
                onShare = {
                    val qrImage = qrState.qrImage
                    if (qrImage != null) {
                        shareQrImage(
                            context = context,
                            qrImage = qrImage,
                            categoryName = qrState.categoryName,
                            wordsCount = qrState.wordsCount
                        )
                    }
                }
            )
        }
        composable(LoadGroupDestination.route) {
            LaunchedEffect(Unit) {
                bottomNavigationController?.hide()
            }
            val loadGroupViewModel: LoadGroupViewModel = viewModel(
                factory = userComponent.vmLoadGroupFactory
            )
            val state by loadGroupViewModel.state.collectAsStateWithLifecycle()
            LoadGroupScreen(
                onQRCodeScanned = { qrData ->
                   loadGroupViewModel.processIntent(LoadGroupIntent.ProcessQRCode(qrData))
                },
                onBack = {
                    innerNavController.popBackStack()
                },
                loadingState = state
            )
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
private fun shareQrImage(
    context: Context,
    qrImage: QrCodeData,
    categoryName: String?,
    wordsCount: Int?
) {
    try {
        val originalBitmap = BitmapFactory.decodeByteArray(qrImage.pixels, 0, qrImage.pixels.size)

        val enhancedBitmap = createEnhancedQrBitmap(originalBitmap, categoryName, wordsCount)

        val file = File(context.cacheDir, "qr_code_${System.currentTimeMillis()}.png")
        FileOutputStream(file).use { stream ->
            enhancedBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        }

        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            file
        )

        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "image/png"
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        context.startActivity(Intent.createChooser(shareIntent, "Share QR Code"))

    } catch (e: Exception) {
        Toast.makeText(context, "Cannot share QR code", Toast.LENGTH_SHORT).show()
    }
}
private fun createEnhancedQrBitmap(
    originalBitmap: Bitmap,
    categoryName: String?,
    wordsCount: Int?
): Bitmap {
    val qrPadding = 60
    val textPadding = 40
    val cornerRadius = 20f
    val backgroundColor = Color.WHITE
    val textColor = Color.BLACK

    val qrWidth = originalBitmap.width + qrPadding * 2
    val qrHeight = originalBitmap.height + qrPadding * 2

    val textHeight = if (categoryName != null || wordsCount != null) 120 else 0
    val totalWidth = qrWidth
    val totalHeight = qrHeight + textHeight

    val resultBitmap = Bitmap.createBitmap(totalWidth, totalHeight, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(resultBitmap)

    canvas.drawColor(backgroundColor)

    val paint = Paint().apply {
        color = textColor
        isAntiAlias = true
        textSize = 36f
    }

    var currentY = textPadding.toFloat()

    categoryName?.let { name ->
        paint.textSize = 32f
        paint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        val textWidth = paint.measureText(name)
        canvas.drawText(name, (totalWidth - textWidth) / 2, currentY, paint)
        currentY += 50f
    }

    wordsCount?.let { count ->
        paint.textSize = 28f
        paint.typeface = Typeface.DEFAULT
        val wordsText = "$count words"
        val textWidth = paint.measureText(wordsText)
        canvas.drawText(wordsText, (totalWidth - textWidth) / 2, currentY, paint)
    }

    val linePaint = Paint().apply {
        color = Color.LTGRAY
        strokeWidth = 2f
    }
    canvas.drawLine(20f, textHeight - 10f, totalWidth - 20f, textHeight - 10f, linePaint)

    val borderPaint = Paint().apply {
        color = Color.LTGRAY
        style = Paint.Style.STROKE
        strokeWidth = 3f
        isAntiAlias = true
    }

    val qrRect = RectF(
        qrPadding - 4f,
        textHeight + qrPadding - 4f,
        totalWidth - qrPadding + 4f,
        totalHeight - qrPadding + 4f
    )
    canvas.drawRoundRect(qrRect, cornerRadius, cornerRadius, borderPaint)

    canvas.drawBitmap(
        originalBitmap,
        qrPadding.toFloat(),
        textHeight + qrPadding.toFloat(),
        null
    )

    return resultBitmap
}