@file:OptIn(ExperimentalMaterial3Api::class)


package com.ad_coding.noteappcourse.ui.screen.note

import DatePickerFecha
import EstadoFecha
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.ad_coding.noteappcourse.Alarma.AlarmItem
import com.ad_coding.noteappcourse.Alarma.AlarmScheduler
import com.ad_coding.noteappcourse.componentes.AudioRecorderButton
import com.ad_coding.noteappcourse.componentes.BotonD
import com.ad_coding.noteappcourse.componentes.CameraButton
import com.ad_coding.noteappcourse.componentes.GalleryVideoPicker
import com.ad_coding.noteappcourse.componentes.MultimediaPicker
import java.time.LocalDateTime


@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun NoteScreen(
    estadoFecha: EstadoFecha,
    alarmScheduler: AlarmScheduler,
    state: NoteState,
    onEvent: (NoteEvent) -> Unit,

    ) {
    var alarma: AlarmItem? =null

    Scaffold(modifier = Modifier.verticalScroll(rememberScrollState()),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "NUEVO") },
                navigationIcon = {
                    IconButton(
                        onClick = { onEvent(NoteEvent.NavigateBack) },
                        // Icon size adjusted
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = "REGRESAR"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { onEvent(NoteEvent.DeleteNote) },
                        // Icon size adjusted
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Delete,
                            contentDescription = "BORRAR"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 15.dp, vertical = 15.dp),
        ) {
            //var multimediaUris by remember { mutableStateOf<List<String>>(listOf()) }
            BotonD(onEvent)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                MultimediaPicker(onEvent,state)
                CameraButton(onEvent,state)

                DatePickerFecha(estadoFecha,onEvent)
            }
            Row {
                AudioRecorderButton()
                GalleryVideoPicker()
            }

             //   Log.d("ITEMS STATE NOTESCREEN","------"+state.fotoS+"---------")
            // multimediaUris=state.fotoS

            OutlinedTextField(
                value = state.title,
                onValueChange = { onEvent(NoteEvent.TitleChange(it)) },
                placeholder = { Text(text = "TÍTULO") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            )
            OutlinedTextField(
                value = state.content,
                onValueChange = { onEvent(NoteEvent.ContentChange(it)) },
                placeholder = { Text(text = "CONTENIDO") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = {
                        alarma =
                            AlarmItem(
                                LocalDateTime.parse(estadoFecha.estadoFecha),
                                message = "Hola tienes una tarea pendiente"
                            )
                        alarma = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            AlarmItem(
                                LocalDateTime.parse(estadoFecha.estadoFecha),
                                "Tienes una tarea pendiente"
                            )
                        } else {
                            TODO("VERSION.SDK_INT < O")
                        }
                        ////////////////////////////////////////
                        alarma?.let(alarmScheduler::schedule)
                        onEvent(NoteEvent.Save)
                    },
                    modifier = Modifier.fillMaxWidth(0.5f)
                ) {
                    Text(text = "GUARDAR")
                }
            }
        }
    }
}
