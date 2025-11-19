package com.andreypmi.user_feature.userScreen.nested_screens.loadWords

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.andreypmi.dictionaryforwords.core.ui.R as Rui
import com.andreypmi.user_feature.R
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraPermissionScreen(
    onOpenCamera: () -> Unit,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val cameraPermissionState = rememberPermissionState(
        android.Manifest.permission.CAMERA
    )

    val openSettings = {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", context.packageName, null)
        }
        context.startActivity(intent)
    }

        LaunchedEffect(Unit) {
        if (cameraPermissionState.status is PermissionStatus.Denied &&
            !cameraPermissionState.status.shouldShowRationale
        ) {
            cameraPermissionState.launchPermissionRequest()
        }
    }

    if (cameraPermissionState.status is PermissionStatus.Granted) {
        LaunchedEffect(Unit) {
            onOpenCamera()
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
            Text(stringResource(R.string.opening_camera))
        }
    } else {
        PermissionRequestScreen(
            onRequestPermission = { cameraPermissionState.launchPermissionRequest() },
            onOpenSettings = openSettings,
            onBack = onBack,
            permissionState = cameraPermissionState
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun PermissionRequestScreen(
    onRequestPermission: () -> Unit,
    onOpenSettings: () -> Unit,
    onBack: () -> Unit,
    permissionState: PermissionState
) {
    val isPermanentlyDenied = permissionState.status is PermissionStatus.Denied &&
            !(permissionState.status as PermissionStatus.Denied).shouldShowRationale

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.camera_permission)) },
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
            Text(
                if (isPermanentlyDenied) {
                    stringResource(R.string.camera_access_denied_message)
                } else if (permissionState.status.shouldShowRationale) {
                    stringResource(R.string.camera_permission_required)
                } else {
                    stringResource(R.string.scanner_camera_required)
                },
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            if (isPermanentlyDenied) {
                Button(onClick = onOpenSettings) {
                    Text(stringResource(R.string.open_settings))
                }
            } else {
                Button(onClick = onRequestPermission) {
                    Text(
                        if (permissionState.status.shouldShowRationale) stringResource(R.string.request_again)
                        else stringResource(R.string.request_permission)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = onBack) {
                Text(stringResource(R.string.go_back))
            }
        }
    }
}