package com.example.bythenai.utils

object Constants {
    object FormKey {
        const val FILE = "file"
        const val UPLOAD_PRESET = "upload_preset"
        const val PUBLIC_ID = "public_id"
        const val API_KEY = "api_key"
    }

    enum class FileType(val type: String) {
        VIDEO("video/*")
    }
}