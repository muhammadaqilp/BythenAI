package com.example.bythenai.repository

import com.example.bythenai.model.UploadModel
import com.example.bythenai.utils.Resource
import kotlinx.coroutines.flow.Flow
import java.io.File

interface IUploadRepository {
    suspend fun uploadVideo(videoFile: File): Flow<Resource<UploadModel>>
}