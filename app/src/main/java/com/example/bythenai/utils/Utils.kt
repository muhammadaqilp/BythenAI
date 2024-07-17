package com.example.bythenai.utils

import android.Manifest
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Context.createVideoFile(): File {
    val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val dir = this.getExternalFilesDir(Environment.DIRECTORY_MOVIES)

    return File.createTempFile("video_${timestamp}", ".mp4", dir)
}

fun File.getUri(context: Context): Uri? {
    return FileProvider.getUriForFile(
        context,
        context.applicationContext.packageName + ".fileprovider",
        this
    )
}

fun Context.checkCameraPermission(
    onPermissionGranted: () -> Unit,
    onNeedRequestPermission: () -> Unit
) {
    val permission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
    if (permission == PackageManager.PERMISSION_GRANTED) {
        onPermissionGranted()
    } else {
        onNeedRequestPermission()
    }
}

fun Context.showToastMessage(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.copyToClipboard(message: String) {
    val clipboardManager = this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clipData = ClipData.newPlainText("text", message)
    clipboardManager.setPrimaryClip(clipData)
    this.showToastMessage("Text copied to clipboard")
}