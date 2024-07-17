package com.example.bythenai.network.response

import com.google.gson.annotations.SerializedName

data class UploadResponse(
    @SerializedName("asset_folder")
    val assetFolder: String?,
    @SerializedName("asset_id")
    val assetId: String?,
    @SerializedName("bytes")
    val bytes: Int?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("display_name")
    val displayName: String?,
    @SerializedName("etag")
    val etag: String?,
    @SerializedName("existing")
    val existing: Boolean?,
    @SerializedName("format")
    val format: String?,
    @SerializedName("height")
    val height: Int?,
    @SerializedName("placeholder")
    val placeholder: Boolean?,
    @SerializedName("playback_url")
    val playbackUrl: String?,
    @SerializedName("public_id")
    val publicId: String?,
    @SerializedName("resource_type")
    val resourceType: String?,
    @SerializedName("secure_url")
    val secureUrl: String?,
    @SerializedName("signature")
    val signature: String?,
    @SerializedName("tags")
    val tags: List<Any?>?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("version")
    val version: Int?,
    @SerializedName("version_id")
    val versionId: String?,
    @SerializedName("width")
    val width: Int?
)