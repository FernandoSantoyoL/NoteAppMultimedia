package com.ad_coding.noteappcourse.componentes

import android.media.MediaRecorder
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext

@Composable
fun AudioRecorderButton() {
    val context = LocalContext.current
    val isRecording: MutableState<Boolean> = remember { mutableStateOf(false) }
    val mediaRecorder = remember { MediaRecorder() }

    val startRecording = {
        try {
            // Configurar y preparar MediaRecorder
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            mediaRecorder.prepare()
            mediaRecorder.start()
            isRecording.value = true
        } catch (e: Exception) {
            // Manejar excepciones
        }
    }

    val stopRecording = {
        try {
            mediaRecorder.stop()
            mediaRecorder.reset()
            isRecording.value = false
        } catch (e: Exception) {
            // Manejar excepciones
        }
    }

    Column {
        Button(onClick = {
            if (isRecording.value) {
                stopRecording()
            } else {
                startRecording()
            }
        }) {
            Text(if (isRecording.value) "Detener Grabación" else "Iniciar Grabación")
        }
    }
}

