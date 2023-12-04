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
    var imageBitmaps by remember { mutableStateOf<List<Bitmap>>(listOf()) }

    val openCamera = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            imageBitmaps = imageBitmaps + bitmap
        }
    }

    Column {
        Button(onClick = {
            openCamera.launch(null)
        }) {
            Icon(Icons.Filled.CameraAlt, contentDescription = "Abrir cámara")
        }

        // Mostrar las imágenes si están disponibles
        imageBitmaps.forEach { bitmap ->
            Image(bitmap = bitmap.asImageBitmap(), contentDescription = "Imagen capturada")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCameraButton() {
    CameraButton()
}

