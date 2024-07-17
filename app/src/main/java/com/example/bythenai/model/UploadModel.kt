package com.example.bythenai.model

import com.example.bythenai.network.response.UploadResponse

data class UploadModel(
    val videoName: String = "",
    val videoUrl: String = ""
) {

    companion object {
        fun mapResponseToModel(response: UploadResponse): UploadModel {
            return UploadModel(
                videoName = response.displayName.orEmpty(),
                videoUrl = response.secureUrl.orEmpty()
            )
        }
    }

}
