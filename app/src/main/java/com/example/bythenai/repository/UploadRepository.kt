package com.example.bythenai.repository

import com.example.bythenai.BuildConfig
import com.example.bythenai.model.UploadModel
import com.example.bythenai.network.service.UploadApi
import com.example.bythenai.utils.Constants
import com.example.bythenai.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.util.UUID
import javax.inject.Inject

class UploadRepository @Inject constructor(
    private val uploadApi: UploadApi
) : IUploadRepository {
    override suspend fun uploadVideo(videoFile: File): Flow<Resource<UploadModel>> {
        return flow {
            try {
                emit(Resource.Loading())
                val request = MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart(Constants.FormKey.UPLOAD_PRESET, BuildConfig.UPLOAD_PRESET)
                    .addFormDataPart(Constants.FormKey.PUBLIC_ID, UUID.randomUUID().toString())
                    .addFormDataPart(Constants.FormKey.API_KEY, BuildConfig.API_KEY)
                    .addFormDataPart(
                        Constants.FormKey.FILE,
                        videoFile.name,
                        videoFile.asRequestBody(Constants.FileType.VIDEO.type.toMediaTypeOrNull())
                    ).build()
                val response = uploadApi.uploadVideo(request)
                emit(Resource.Success(data = UploadModel.mapResponseToModel(response)))
            } catch (e: Exception) {
                emit(Resource.Error(message = e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}