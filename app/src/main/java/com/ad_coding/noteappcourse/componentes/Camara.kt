package com.ad_coding.noteappcourse.componentes

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun CameraButton() {
    val context = LocalContext.current

    val openCamera = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        // Trata el bitmap de la imagen aquí
    }

    Column {
        Button(onClick = {
            openCamera.launch(null)
        }) {
            // Usar un icono de cámara
            Icon(Icons.Filled.CameraAlt, contentDescription = "Abrir cámara")
        }
    }
}
