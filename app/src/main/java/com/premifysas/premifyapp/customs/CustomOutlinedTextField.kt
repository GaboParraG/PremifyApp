package com.premifysas.premifyapp.customs

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.premifysas.premifyapp.R

@Composable
fun CustomOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = colorResource(id = R.color.primary_color),
            unfocusedBorderColor = if (value.isNotBlank()) colorResource(id = R.color.primary_color) else colorResource(id = R.color.secondary_color),
            focusedLabelColor = colorResource(id = R.color.primary_color),
            unfocusedLabelColor = if (value.isNotBlank()) colorResource(id = R.color.primary_color) else colorResource(id = R.color.secondary_color),
            cursorColor = colorResource(id = R.color.primary_color),
            focusedTextColor = colorResource(id = R.color.primary_color),
            unfocusedTextColor = if (value.isNotBlank()) colorResource(id = R.color.primary_color) else colorResource(id = R.color.secondary_color)
        ),
        keyboardOptions = keyboardOptions
    )
}