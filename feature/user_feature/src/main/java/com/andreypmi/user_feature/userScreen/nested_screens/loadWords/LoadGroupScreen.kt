package com.andreypmi.user_feature.userScreen.nested_screens.loadWords

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.AlertDialog
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.andreypmi.user_feature.userScreen.nested_screens.loadWords.models.LoadGroupState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoadGroupScreen(
    onQRCodeScanned: (String) -> Unit,
    onBack: () -> Unit,
    loadingState: LoadGroupState
) {
    var showCamera by remember { mutableStateOf(false) }
    var showFilePicker by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Загрузить слова") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (loadingState.isLoading) {
                CircularProgressIndicator()
                Spacer(modifier = Modifier.height(16.dp))
                Text("Загрузка слов...")
            }

            loadingState.error?.let { error ->
                Text(
                    text = "Ошибка: $error",
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            loadingState.successMessage?.let { message ->
                Text(
                    text = message,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )

                loadingState.loadedCategory?.let { category ->
                    Spacer(modifier = Modifier.height(16.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Категория: ${category.categoryName}",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Количество слов: ${category.words.size}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = {
                                    // TODO: Навигация к изучению этой категории
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Начать изучение")
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            if (!loadingState.isLoading) {
                Button(
                    onClick = { showCamera = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Face, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Сканировать QR код камерой")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { showFilePicker = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.AccountCircle, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Выбрать QR код из файла")
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Отсканируйте QR код, чтобы загрузить набор слов, которым поделился другой пользователь",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        if (showCamera) {
            QRCodeScannerDialog(
                onDismiss = { showCamera = false },
                onQRCodeScanned = { qrData ->
                    showCamera = false
                    onQRCodeScanned(qrData)
                }
            )
        }

        if (showFilePicker) {
            FilePickerDialog(
                onDismiss = { showFilePicker = false },
                onFileSelected = { fileUri ->
                    showFilePicker = false
                    onQRCodeScanned(fileUri.toString())
                }
            )
        }
    }
}

@Composable
fun QRCodeScannerDialog(
    onDismiss: () -> Unit,
    onQRCodeScanned: (String) -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .background(Color.Black),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Сканер QR кодов", color = Color.White)
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Отмена")
                    }
                }
            }
        }
    }
}

@Composable
fun FilePickerDialog(
    onDismiss: () -> Unit,
    onFileSelected: (Uri) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Выбор файла") },
        text = { Text("Здесь будет системный выбор файла для выбора изображения с QR кодом") },
        confirmButton = {
            TextButton(onClick = {
                onFileSelected(Uri.parse("content://temp/qr_code.jpg"))
            }) {
                Text("Выбрать файл")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Отмена")
            }
        }
    )
}