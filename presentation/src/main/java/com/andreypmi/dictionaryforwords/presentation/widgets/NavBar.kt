package com.andreypmi.dictionaryforwords.presentation.widgets

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.andreypmi.dictionaryforwords.presentation.navigation.DictionaryNavBarDestination

@Composable
internal fun NavBar(
    currentDestination: String?,
    destinations: List<DictionaryNavBarDestination>,
    onNavigateToTopLevel: (topRoute: String) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(modifier = Modifier.height(50.dp)) {
        destinations.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(painterResource(id = item.iconId), contentDescription = stringResource(item.titleId))},
        //        label = { Text(stringResource(item.titleId)) },
                selected = currentDestination == item.route,
                onClick = { onNavigateToTopLevel(item.route) },
                alwaysShowLabel = true,
                modifier = modifier
                )
        }
    }
}