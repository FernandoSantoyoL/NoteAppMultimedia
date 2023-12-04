package com.ad_coding.noteappcourse.componentes

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PermMedia
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun MultimediaPicker() {
    // Estado para almacenar las URIs de los archivos multimedia seleccionados
    val multimediaUris = remember { mutableStateOf<List<Uri>>(listOf()) }
    val context = LocalContext.current

    // Estado para controlar la visibilidad del AlertDialog
    val showDialog = remember { mutableStateOf(false) }

    // Inicializar el ActivityResultLauncher
    val multimediaPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            // Obtener las URIs de los archivos seleccionados
            val clipData = result.data?.clipData
            if (clipData != null) {
                val uris = mutableListOf<Uri>()
                for (i in 0 until clipData.itemCount) {
                    uris.add(clipData.getItemAt(i).uri)
                }
                multimediaUris.value = uris
            } else {
                result.data?.data?.let { uri ->
                    multimediaUris.value = listOf(uri)
                }
            }
        }
    }

    Column {
        Button(onClick = {
            // Mostrar el AlertDialog
            showDialog.value = true
        }) {
            // Usar un icono de multimedia
            Icon(Icons.Filled.PermMedia, contentDescription = "Seleccionar Multimedia")
        }

        // Mostrar las URIs de los archivos seleccionados
        multimediaUris.value.forEach { uri ->
            AsyncImage(
                model = uri,
                contentDescription = null,
                modifier = Modifier.width(150.dp).height(200.dp)
            )
        }

        // AlertDialog
        if (showDialog.value) {
            AlertDialog(
                onDismissRequest = { showDialog.value = false },
                title = { Text(text = "Confirmación") },
                text = { Text(text = "¿Estás seguro de seleccionar archivos multimedia?") },
                confirmButton = {
                    Button(onClick = {
                        showDialog.value = false
                        val intent = Intent(Intent.ACTION_PICK).apply {
                            type = "image/* video/*"
                            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                        }
                        multimediaPickerLauncher.launch(intent)
                    }) {
                        Text("Aceptar")
                    }
                },
                dismissButton = {
                    Button(onClick = { showDialog.value = false }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}
