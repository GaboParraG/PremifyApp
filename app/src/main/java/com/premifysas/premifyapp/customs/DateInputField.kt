package com.premifysas.premifyapp.customs

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.premifysas.premifyapp.R
import java.util.*

@Composable
fun DateInputField(
    date: String,
    onDateSelected: (String) -> Unit
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val selectedDate = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
                onDateSelected(selectedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).apply {
            datePicker.minDate = System.currentTimeMillis() // 🚫 Bloquear fechas anteriores a hoy
        }
    }

    OutlinedTextField(
        value = date,
        onValueChange = {},
        readOnly = true,
        label = { Text("Fecha") },
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = "Seleccionar fecha",
                modifier = Modifier.clickable {
                    datePickerDialog.show()
                }
            )
        },
        modifier = Modifier
            .fillMaxWidth(0.9f),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = colorResource(id = R.color.primary_color),
            unfocusedBorderColor = if (date.isNotBlank()) colorResource(id = R.color.primary_color) else colorResource(id = R.color.secondary_color),
            focusedLabelColor = colorResource(id = R.color.primary_color),
            unfocusedLabelColor = if (date.isNotBlank()) colorResource(id = R.color.primary_color) else colorResource(id = R.color.secondary_color),
            cursorColor = colorResource(id = R.color.primary_color),
            focusedTextColor = colorResource(id = R.color.primary_color),
            unfocusedTextColor = if (date.isNotBlank()) colorResource(id = R.color.primary_color) else colorResource(id = R.color.secondary_color)
        )
    )
}