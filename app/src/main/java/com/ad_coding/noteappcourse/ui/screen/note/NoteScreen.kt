@file:OptIn(ExperimentalMaterial3Api::class)


package com.ad_coding.noteappcourse.ui.screen.note

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ad_coding.noteappcourse.componentes.BarraBuscar
import com.ad_coding.noteappcourse.componentes.BotonD
import com.ad_coding.noteappcourse.componentes.CameraButton
import com.ad_coding.noteappcourse.componentes.DatePickerFecha
import com.ad_coding.noteappcourse.componentes.MultimediaPicker


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(
    state: NoteState,
    onEvent: (NoteEvent) -> Unit,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "NUEVO") },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onEvent(NoteEvent.NavigateBack)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = "REGRESAR"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            onEvent(NoteEvent.DeleteNote)
                        }
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
                .padding(
                    horizontal = 15.dp,
                    vertical = 15.dp
                ),
            //     verticalArrangement = Arrangement.spacedBy(10.dp, 0.dp)
        ) {
            BotonD()
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                MultimediaPicker()
                CameraButton()
            }
            DatePickerFecha()
            OutlinedTextField(
                value = state.title,
                onValueChange = {
                    onEvent(NoteEvent.TitleChange(it))
                },
                placeholder = {
                    Text(text = "TITULO")
                }, modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = state.content,
                onValueChange = {
                    onEvent(NoteEvent.ContentChange(it))
                },
                placeholder = {
                    Text(text = "CONTENIDO")
                }, modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
            )

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = {
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

