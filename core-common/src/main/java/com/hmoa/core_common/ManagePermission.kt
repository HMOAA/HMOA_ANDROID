package com.hmoa.core_common

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat

const val PERMISSION_REQUEST_CODE = 1001
val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) arrayOf(
    Manifest.permission.POST_NOTIFICATIONS,
    Manifest.permission.READ_MEDIA_IMAGES
) else {
    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
}

val galleryPermission = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) Manifest.permission.READ_MEDIA_IMAGES
                        else Manifest.permission.READ_EXTERNAL_STORAGE

fun checkPermission(context: Context, permission: String): Boolean = ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED