package com.andreypmi.user_feature.userScreen.nested_screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentDataType.Companion.Text
import com.andreypmi.user_feature.userScreen.models.ShareGroupState

@Composable
fun ShareGroupScreen(
    groupsState: ShareGroupState,
    onGroupSelected: (Int) -> Unit,
    onBack: () -> Unit
) {
    // TODO: Реализовать экран выбора группы для шаринга
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("ShareGroupScreen - в разработке")
    }
}
