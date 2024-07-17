package com.example.bythenai.ui.upload.screen.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bythenai.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetNetworkErrorDialog(
    onDismiss: () -> Unit,
    onTryAgain: () -> Unit
) {
    val modalBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    LaunchedEffect(Unit) {
        modalBottomSheetState.expand()
    }

if (modalBottomSheetState.isVisible) {
    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
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
                modifier = Modifier.size(120.dp),
                painter = painterResource(id = R.drawable.ic_network_problem),
                contentDescription = stringResource(id = R.string.icon_network_problem)
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.no_internet_connection)
            )
            Text(text = stringResource(id = R.string.no_internet_connection_desc))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                onClick = { onTryAgain() }) {
                Text(text = stringResource(id = R.string.try_again))
            }
        }
    }
}
}

@Preview
@Composable
private fun BottomSheetNetworkErrorDialogPreview() {
    BottomSheetNetworkErrorDialog(onDismiss = {}, onTryAgain = {})
}