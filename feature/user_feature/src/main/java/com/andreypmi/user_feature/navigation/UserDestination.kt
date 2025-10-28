package com.andreypmi.user_feature.navigation

import com.andreypmi.dictionaryforwords.core.ui.R
import com.andreypmi.navigation_api.DictionaryNavBarDestination
import com.andreypmi.navigation_api.DictionaryNavDestination


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