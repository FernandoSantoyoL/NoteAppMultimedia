package com.ad_coding.noteappcourse.componentes

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.widget.VideoView
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun GalleryVideoPicker() {
    var videoUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

    val videoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            videoUri = result.data?.data // Obtener el URI del video seleccionado
        }
    }

    Column {
        IconButton(onClick = {
            val intent = Intent(Intent.ACTION_PICK).apply {
                type = "video/*"
            }
            videoPickerLauncher.launch(intent)
        }) {
            Icon(imageVector = Icons.Filled.Videocam, contentDescription = "Seleccionar Video")
        }

        videoUri?.let { uri ->
            val density = LocalDensity.current
            val videoWidth = 100.dp
            val videoHeight = 150.dp

            AndroidView(
                factory = { ctx ->
                    VideoView(ctx).apply {
                        setVideoURI(uri)
                        start() // Reproducir automáticamente
                    }
                },
                modifier = Modifier.size(width = videoWidth, height = videoHeight)
            )
        }
    }
}