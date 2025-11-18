package com.andreypmi.user_feature.navigation

import androidx.navigation.navDeepLink
import com.andreypmi.dictionaryforwords.core.ui.R
import com.andreypmi.navigation_api.DictionaryNavBarDestination
import com.andreypmi.navigation_api.DictionaryNavDestination
import com.andreypmi.user_feature.navigation.QRCodeDestination.categoryIdArg
import java.net.URLEncoder


data object UserTopLevelDestination : DictionaryNavBarDestination {
    override val iconId = R.drawable.ic_person
    override val titleId = R.string.profile
    override val route = UserDestination.route
}

object UserDestination : DictionaryNavDestination {
    override val route = "user"
}

object UserMainDestination : DictionaryNavDestination {
    override val route = "user_main"
}

object ShareGroupDestination : DictionaryNavDestination {
    override val route = "share_group"
}

object LoadGroupDestination : DictionaryNavDestination {
    override val route = "load_group"
}

object CameraPermission : DictionaryNavDestination {
    override val route = "camera_permission"
}

object CameraScanner : DictionaryNavDestination {
    override val route = "camera_scanner"
}

object ScannerResult : DictionaryNavDestination {
    override val route = "scanner_result"
    val deepLinks = listOf(
        navDeepLink {
            uriPattern = "https://dictionaryforwords.web.app/scanner/{qrData}"
        }
    )
    const val qrArg = "qrData"
    val routeWithArgs = "${ScannerResult.route}/{$qrArg}"
    fun createRoute(qrData: String): String {
        val encodedQrData = URLEncoder.encode(qrData, "UTF-8")
        return "${route}/$encodedQrData"
    }
}

object FilePicker : DictionaryNavDestination {
    override val route = "file_picker"
}

object QRCodeDestination : DictionaryNavDestination {
    override val route = "qr_code"
    const val categoryIdArg = "category_id"
    val routeWithArgs = "$route/{$categoryIdArg}"

    fun createRoute(categoryId: String): String = "$route/$categoryId"
}

object NotificationsDestination : DictionaryNavDestination {
    override val route = "notifications"
}

object SettingsDestination : DictionaryNavDestination {
    override val route = "settings"
}