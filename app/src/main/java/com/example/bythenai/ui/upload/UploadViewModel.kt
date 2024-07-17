package com.example.bythenai.ui.upload

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bythenai.model.UploadModel
import com.example.bythenai.repository.IUploadRepository
import com.example.bythenai.ui.upload.state.UploadState
import com.example.bythenai.ui.upload.state.UploadViewState
import com.example.bythenai.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class UploadViewModel @Inject constructor(
    private val uploadRepository: IUploadRepository
) : ViewModel() {

    private val _viewState = MutableStateFlow(UploadViewState())
    val viewState: StateFlow<UploadViewState> = _viewState

    fun uploadVideo(videoFile: File) {
        viewModelScope.launch {
            uploadRepository.uploadVideo(videoFile).collect { model ->
                when (model) {
                    is Resource.Loading -> {
                        _viewState.update {
                            UploadViewState(
                                uploadState = UploadState.LOADING,
                                isVideoRecorded = true
                            )
                        }
                    }

                    is Resource.Success -> {
                        _viewState.update {
                            UploadViewState(
                                uploadState = UploadState.SUCCESS,
                                uploadData = model.data ?: UploadModel(),
                                isVideoRecorded = false
                            )
                        }
                    }

                    else -> {
                        _viewState.update {
                            UploadViewState(
                                uploadState = UploadState.ERROR,
                                errorMessage = model.message.orEmpty(),
                                isVideoRecorded = true
                            )
                        }
                    }
                }
            }
        }
    }

    fun updateVideoRecorded(state: Boolean) {
        _viewState.update { it.copy(isVideoRecorded = state) }
    }

    fun updateErrorState() {
        _viewState.update { it.copy(uploadState = UploadState.NONE) }
    }

    fun resetState() {
        _viewState.update { UploadViewState() }
    }

}