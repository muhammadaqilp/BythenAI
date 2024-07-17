package com.example.bythenai.ui.upload.screen

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bythenai.R
import com.example.bythenai.utils.checkCameraPermission

@Composable
fun UploadScreen(
    modifier: Modifier = Modifier,
    isVideoRecorded: Boolean,
    onRecordClicked: () -> Unit,
    onUploadClicked: () -> Unit
) {

    val context = LocalContext.current

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                onRecordClicked()
            } else {
                Toast.makeText(context, "Camera Permission is Required", Toast.LENGTH_SHORT).show()
            }
        })

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isVideoRecorded) {
            Text(text = stringResource(id = R.string.video_is_already_taken))
        }

        Spacer(modifier = Modifier.padding(8.dp))

        Button(onClick = {
            context.checkCameraPermission(
                onPermissionGranted = {
                    onRecordClicked()
                },
                onNeedRequestPermission = {
                    permissionLauncher.launch(Manifest.permission.CAMERA)
                }
            )
        }) {
            Text(text = if (isVideoRecorded) {
                stringResource(id = R.string.retake_video)
            } else {
                stringResource(id = R.string.record_video)
            })
        }

        Button(onClick = { onUploadClicked() }, enabled = isVideoRecorded) {
            Text(text = stringResource(id = R.string.upload_video))
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Send,
                contentDescription = "Upload",
                modifier = Modifier.padding(start = 4.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun UploadScreenPreview() {
    UploadScreen(isVideoRecorded = false, onRecordClicked = {}, onUploadClicked = {})
}