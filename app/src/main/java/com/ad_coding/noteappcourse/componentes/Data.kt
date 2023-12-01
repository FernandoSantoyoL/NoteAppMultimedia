package com.ad_coding.noteappcourse.componentes

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import java.util.Calendar

import androidx.compose.runtime.*


@Composable
fun DatePickerFecha() {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    var date by remember { mutableStateOf("${day}/${month + 1}/${year}") }

    Column {
        Button(onClick = {
            DatePickerDialog(context, { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                date = "${selectedDayOfMonth}/${selectedMonth + 1}/$selectedYear"
            }, year, month, day).show()
        }) {
            Text("Seleccionar Fecha")
        }

        Text(text = "Fecha seleccionada: $date")
    }
}

