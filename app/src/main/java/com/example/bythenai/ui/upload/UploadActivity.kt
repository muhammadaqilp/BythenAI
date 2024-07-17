package com.example.bythenai.ui.upload

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.bythenai.ui.theme.BythenAITheme
import com.example.bythenai.ui.upload.screen.UploadScreen
import com.example.bythenai.ui.upload.screen.component.BottomSheetNetworkErrorDialog
import com.example.bythenai.ui.upload.screen.component.BottomSheetType
import com.example.bythenai.ui.upload.screen.component.BottomSheetUploadStatusDialog
import com.example.bythenai.ui.upload.screen.component.ProgressDialog
import com.example.bythenai.ui.upload.state.UploadState
import com.example.bythenai.utils.checknetwork.NetworkStatus
import com.example.bythenai.utils.checknetwork.rememberConnectivityState
import com.example.bythenai.utils.copyToClipboard
import com.example.bythenai.utils.createVideoFile
import com.example.bythenai.utils.getUri
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class UploadActivity : ComponentActivity() {

    private val viewModel: UploadViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var videoFile by remember { mutableStateOf<File?>(value = null) }
            var videoUri by remember { mutableStateOf<Uri?>(value = null) }
            var showErrorBottomSheet by remember { mutableStateOf(false) }

            val connection by rememberConnectivityState()

            val viewState by viewModel.viewState.collectAsState()

            val videoLauncher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.CaptureVideo(),
                onResult = { isSuccess ->
                    viewModel.updateVideoRecorded(isSuccess)
                }
            )

            LaunchedEffect(connection) {
                if (connection === NetworkStatus.Unavailable) {
                    //show dialog to inform user network unavailable
                    showErrorBottomSheet = true
                }
            }

            BythenAITheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    UploadScreen(
                        modifier = Modifier.padding(innerPadding),
                        isVideoRecorded = viewState.isVideoRecorded,
                        onRecordClicked = {
                            videoFile = createVideoFile()
                            videoUri = videoFile?.getUri(this)

                            videoUri?.let {
                                videoLauncher.launch(it)
                            }
                        },
                        onUploadClicked = {
                            videoFile?.let { file ->
                                viewModel.uploadVideo(file)
                            }
                        }
                    )

                    when (viewState.uploadState) {
                        UploadState.LOADING -> {
                            ProgressDialog()
                        }

                        UploadState.SUCCESS -> {
                            BottomSheetUploadStatusDialog(
                                type = BottomSheetType.SUCCESS,
                                model = viewState.uploadData,
                                onLinkClicked = {
                                    copyToClipboard(viewState.uploadData.videoUrl)
                                },
                                onDismissDialog = {
                                    videoFile?.deleteRecursively()
                                    videoUri = null
                                    viewModel.resetState()
                                }
                            )
                        }

                        UploadState.ERROR -> {
                            BottomSheetUploadStatusDialog(
                                type = BottomSheetType.ERROR,
                                onDismissDialog = {
                                    viewModel.updateErrorState()
                                }
                            )
                        }

                        UploadState.NONE -> {
                            //do nothing
                        }
                    }

                    if (showErrorBottomSheet) {
                        BottomSheetNetworkErrorDialog(
                            onDismiss = {
                                startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
                            },
                            onTryAgain = {
                                if (!this.isFinishing) {
                                    finish()
                                    startActivity(intent)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}