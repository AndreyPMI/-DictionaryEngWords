package com.andreypmi.user_feature.userScreen.nested_screens.shared_group

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.andreypmi.user_feature.R
import com.andreypmi.user_feature.userScreen.nested_screens.shared_group.models.ShareGroupCategory
import com.andreypmi.user_feature.userScreen.nested_screens.shared_group.models.ShareGroupState
import com.andreypmi.user_feature.userScreen.nested_screens.shared_group.viewmodels.ShareGroupIntent
import com.andreypmi.dictionaryforwords.core.ui.R as Rui

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShareGroupScreen(
    state: ShareGroupState,
    onIntent: (ShareGroupIntent) -> Unit,
    onNavigateToQR: (String) -> Unit,
    onBack: () -> Unit
) {
    val lazyListState = rememberLazyListState()

    LaunchedEffect(lazyListState) {
        snapshotFlow { lazyListState.layoutInfo.visibleItemsInfo }
            .collect { visibleItems ->
                if (state.hasMore && !state.isLoadingMore) {
                    val lastVisibleItem = visibleItems.lastOrNull()
                    if (lastVisibleItem?.index == state.categories.lastIndex - 2) {
                        onIntent(ShareGroupIntent.LoadNextPage)
                    }
                }
            }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.share_a_group)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            painter = painterResource(Rui.drawable.arrow_back_24dp),
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }
            )
        }
    ) { padding ->
        when {
            state.isLoading && state.categories.isEmpty() -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            state.error != null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(stringResource(R.string.error, state.error))
                        Spacer(Modifier.height(16.dp))
                        Button(onClick = { }) {
                            Text(stringResource(R.string.repeat))
                        }
                    }
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.padding(padding),
                    state = lazyListState
                ) {
                    items(state.categories) { category ->
                        CategoryItem(
                            category = category,
                            onClick = { onNavigateToQR(category.id) }
                        )
                    }

                    if (state.isLoadingMore) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }

                    if (!state.hasMore && state.categories.isNotEmpty()) {
                        item {
                            Text(
                                text = stringResource(R.string.all_categories_are_loaded),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryItem(
    category: ShareGroupCategory,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = category.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = stringResource(R.string.words, category.wordCount),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Icon(
                painter = painterResource(Rui.drawable.arrow_back_24dp),
                contentDescription = stringResource(R.string.to_share),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}