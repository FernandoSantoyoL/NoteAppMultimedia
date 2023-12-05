package com.ad_coding.noteappcourse.componentes


import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import java.util.*

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
                scheduleAlarm(context, selectedYear, selectedMonth, selectedDayOfMonth)
            }, year, month, day).show()
        }) {
            Text("Seleccionar Fecha")
        }

        Text(text = "Fecha seleccionada: $date")
    }
}

private fun scheduleAlarm(context: Context, year: Int, month: Int, day: Int) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, MyAlarmReceiver::class.java)
    val pendingIntent =
        PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

    val calendar = Calendar.getInstance()
    calendar.set(year, month, day, 8, 0) // Ajusta la hora y los minutos según tus necesidades

    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
}

