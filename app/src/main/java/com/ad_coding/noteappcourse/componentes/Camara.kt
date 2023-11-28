package com.ad_coding.noteappcourse.componentes

import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CameraButton() {
    var imageBitmap by remember { mutableStateOf<Bitmap?>(null) }

    val openCamera = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        // Actualiza el estado con la imagen capturada
        imageBitmap = bitmap
    }

    Column {
        Button(onClick = {
            openCamera.launch(null)
        }) {
            Icon(Icons.Filled.CameraAlt, contentDescription = "Abrir cámara")
        }

        // Mostrar la imagen si está disponible
        imageBitmap?.let { bitmap ->
            Image(bitmap = bitmap.asImageBitmap(), contentDescription = "Imagen capturada")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCameraButton() {
    CameraButton()
}

