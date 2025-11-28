package com.andreypmi.user_feature.userScreen.nested_screens.loadWords

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.andreypmi.core_domain.models.Word
import com.andreypmi.dictionaryforwords.core.ui.R as Rui
import com.andreypmi.user_feature.R
import com.andreypmi.user_feature.userScreen.nested_screens.loadWords.models.ScanState
import com.andreypmi.user_feature.userScreen.nested_screens.loadWords.models.ScannedCategory

@Composable
fun ProcessQRScreen(
    state: ScanState,
    scannedCategory: ScannedCategory?,
    onBack: () -> Unit,
    onRescan: () -> Unit,
    onSave: () -> Unit
) {
    when (state) {
        is ScanState.Idle -> {
            LoadingScreen(onBack = onBack)
        }

        is ScanState.Loading -> {
            LoadingScreen(onBack = onBack)
        }

        is ScanState.Saving -> {
            LoadingScreen(onBack = onBack, message = stringResource(R.string.saving_category))
        }
        is ScanState.SavedSuccessfully -> {
            LoadingScreen(onBack = onBack, message = stringResource(R.string.category_saved))
        }

        is ScanState.Success -> {
            ScanResultScreen(
                category = scannedCategory,
                onBack = onBack,
                onRescan = onRescan,
                onSave = onSave
            )
        }

        is ScanState.Error -> {
            val errorMessage = state.message
            ErrorResultScreen(
                errorMessage = stringResource(errorMessage),
                onBack = onBack,
                onRescan = onRescan
            )
        }

        else -> {
            LoadingScreen(onBack = onBack)
        }
    }
}

@Composable
fun ScanResultScreen(
    category: ScannedCategory?,
    onBack: () -> Unit,
    onRescan: () -> Unit,
    onSave: () -> Unit
) {
    if (category != null) {
        SuccessResultScreen(
            category = category,
            onBack = onBack,
            onSave = onSave
        )
    } else {
        ErrorResultScreen(
            errorMessage = stringResource(R.string.category_data_not_found),
            onBack = onBack,
            onRescan = onRescan
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuccessResultScreen(
    category: ScannedCategory,
    onBack: () -> Unit,
    onSave: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.category_loaded)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            painter = painterResource(Rui.drawable.arrow_back_24dp),
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        category.categoryName,
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        stringResource(R.string.words_count, category.wordsCount),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            ) {
                items(category.words) { word ->
                    WordItem(word = word)
                }
            }

            Button(
                onClick = {
                    onSave()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(stringResource(R.string.add_to_my_categories))
            }
        }
    }
}

@Composable
fun WordItem(word: Word) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                word.word,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                word.translate,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )

            word.description?.let { description ->
                if (description.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontStyle = FontStyle.Italic
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoadingScreen(onBack: () -> Unit, message: String = stringResource(R.string.loading_category)) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.processing_qr_code)) },
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator()
                Spacer(modifier = Modifier.height(16.dp))
                Text(message)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ErrorResultScreen(
    errorMessage: String,
    onBack: () -> Unit,
    onRescan: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.load_error)) },
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(Rui.drawable.close_24dp),
                contentDescription = stringResource(R.string.error),
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.error
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                stringResource(R.string.category_load_failed),
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                errorMessage,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(onClick = onRescan) {
                Text(stringResource(R.string.try_again))
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = onBack) {
                Text(stringResource(R.string.return_back))
            }
        }
    }
}