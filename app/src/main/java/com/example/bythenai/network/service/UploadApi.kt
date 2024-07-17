package com.example.bythenai.network.service

import com.example.bythenai.network.response.UploadResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface UploadApi {

    @POST("v1_1/dk3lhojel/video/upload")
    suspend fun uploadVideo(@Body body: RequestBody): UploadResponse

}