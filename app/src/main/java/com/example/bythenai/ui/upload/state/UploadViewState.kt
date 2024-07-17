package com.example.bythenai.ui.upload.state

import com.example.bythenai.model.UploadModel

data class UploadViewState(
    val uploadState: UploadState = UploadState.NONE,
    val uploadData: UploadModel = UploadModel(),
    val errorMessage: String = "",
    val isVideoRecorded: Boolean = false
)

enum class UploadState {
    LOADING, SUCCESS, ERROR, NONE
}
