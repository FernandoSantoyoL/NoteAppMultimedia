package com.ad_coding.noteappcourse.componentes

import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.ui.platform.LocalContext

@Composable
fun CameraButton() {
    val context = LocalContext.current

    val openCamera = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->

    }

    Button(onClick = {
        openCamera.launch(null)
    }) {
        Text("Cámara")
        }
}