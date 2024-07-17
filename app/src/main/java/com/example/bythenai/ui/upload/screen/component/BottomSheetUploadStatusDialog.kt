package com.example.bythenai.ui.upload.screen.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bythenai.R
import com.example.bythenai.model.UploadModel

enum class BottomSheetType {
    SUCCESS, ERROR
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetUploadStatusDialog(
    type: BottomSheetType,
    model: UploadModel = UploadModel(),
    onDismissDialog: () -> Unit,
    onLinkClicked: () -> Unit = {}
) {
    val modalBottomSheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = { onDismissDialog() },
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.size(80.dp),
                painter = if (type == BottomSheetType.SUCCESS) {
                    painterResource(id = R.drawable.ic_success)
                } else {
                    painterResource(id = R.drawable.ic_error)
                },
                contentDescription = stringResource(id = R.string.icon_upload_status)
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                style = MaterialTheme.typography.titleLarge,
                text = if (type == BottomSheetType.SUCCESS) {
                    stringResource(id = R.string.success_upload_video)
                } else {
                    stringResource(id = R.string.failed_upload_video)
                },
                textAlign = TextAlign.Center
            )
            if (type == BottomSheetType.SUCCESS) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                        .clickable { onLinkClicked() },
                    text = stringResource(id = R.string.success_upload_video_desc, model.videoUrl),
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Preview
@Composable
private fun BottomSheetUploadStatusDialogPreview() {
    BottomSheetUploadStatusDialog(type = BottomSheetType.SUCCESS, onDismissDialog = {})
}