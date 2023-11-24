package com.ad_coding.noteappcourse.componentes

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun MultimediaPicker() {
    // Estado para almacenar la URI del archivo multimedia seleccionado
    val multimediaUri = remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

    // Inicializar el ActivityResultLauncher
    val multimediaPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // Obtener la URI del archivo seleccionado
            multimediaUri.value = result.data?.data
        }
    }

    Column {
        Button(onClick = {
            // Abrir el selector de archivos multimedia
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/* video/*" // Para seleccionar imágenes y videos
            multimediaPickerLauncher.launch(intent)
        }) {
            Text(text = "Seleccionar Multimedia")
        }

        // Mostrar la URI del archivo seleccionado
        multimediaUri.value?.let { uri ->
            AsyncImage(
                model = uri,
                contentDescription = null,
                modifier = Modifier.width(150.dp).height(200.dp)
            )
        }
         }
     }
