package com.ad_coding.noteappcourse.componentes

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import java.util.*

@Preview
@Composable
fun DatePickerFecha() {
    val context = LocalContext.current
    val calendar = remember { Calendar.getInstance() }
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    // Estado para almacenar la fecha seleccionada
    var date = remember { "${day}/${month + 1}/${year}" }

    Button(onClick = {
        // Mostrar DatePickerDialog
        DatePickerDialog(context, { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            // Actualizar la fecha seleccionada
            date = "${dayOfMonth}/${month + 1}/${year}"
        }, year, month, day).show()
    }) {
        Text(text = "Seleccionar Fecha")
    }

    // Mostrar la fecha seleccionada
    Text(text = "Fecha seleccionada: $date")
}

