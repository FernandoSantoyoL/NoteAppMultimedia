    package com.ad_coding.noteappcourse.componentes

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.ui.PlayerView
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.ad_coding.noteappcourse.ui.screen.note.NoteEvent
import com.ad_coding.noteappcourse.ui.screen.note.NoteState
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import java.io.IOException

    @Composable
fun CameraButton(
        onEvent: (NoteEvent) -> Unit,
        state: NoteState,
) {
    //Log.d("INICIOCAMARA","------------------")
        var URI : String =""
        var FotosUris by remember { mutableStateOf<List<String>>(listOf()) }
    var imageBitmaps by remember { mutableStateOf<List<Bitmap>>(listOf()) }
    var selectedBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var showDeleteDialog by remember { mutableStateOf(false) }
        var selectedUri by remember { mutableStateOf<Uri?>(null) }
        val context = LocalContext.current
        Log.d("STATEFOTOCAMARA",state.fotoC.toString()+"---------")
        FotosUris = state.fotoC

    val openCamera = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { bitmap ->
        if (bitmap) {
        //    Log.d("EVENTOFOTOCAMB","---------")
            onEvent(NoteEvent.FotoCamaraCambio(listOf(URI)+FotosUris))
            FotosUris = state.fotoC
          //  Log.d("FOTOSURIS",FotosUris.toString()+"--SE GUARDO-------")
            //Log.d("GUARDARFOTO","--SE GUARDO-------")

        }
    }
        val openCameraP = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.TakePicturePreview()
        ) { bitmap ->
            if (bitmap != null) {
                openCamera.launch(Uri.parse(URI))
                imageBitmaps = imageBitmaps + bitmap
            }
        }

    Column {
        Button(onClick = {
            Log.d("ABRIRCAMARA","--ONCLICK-------")
           val uri = ComposeFileProvider.getImageUri(context)
            Log.d("URI",uri.toString())
            URI = uri.toString()
            openCamera.launch(uri)
            Log.d("GUARDARFOTO","---ONCLICK------")

        }) {
            Icon(Icons.Filled.CameraAlt, contentDescription = "Abrir cámara")
        }
       // Dibujar(uri = URI)
        FotosUris = state.fotoC
        LazyColumn(modifier = Modifier.height(150.dp)) {
            Log.d("URIARCHIVOLAZY",FotosUris.toString()+"")
            items(FotosUris) { bitmap ->
                Image(
                    painter = rememberAsyncImagePainter(model = Uri.parse(bitmap)),
                    contentDescription = null,
                    modifier = Modifier
                        .width(100.dp)
                        .height(150.dp)
                        .clickable {
                            selectedUri = Uri.parse(bitmap)
                            showDeleteDialog = true
                        }

                )
            }
        }

        if (showDeleteDialog) {
            AlertDialog(
                onDismissRequest = { showDeleteDialog = false },
                title = { Text("Eliminar imagen") },
                text = { Text("¿Deseas eliminar esta imagen?") },
                confirmButton = {
                    Button(onClick = {
                        showDeleteDialog = false
                        selectedBitmap?.let { bitmap ->
                            imageBitmaps = imageBitmaps.filter { it != bitmap }
                        }
                    }) {
                        Text("Eliminar")
                    }
                },
                dismissButton = {
                    Button(onClick = { showDeleteDialog = false }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}

