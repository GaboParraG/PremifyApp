package com.premifysas.premifyapp.customs

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.premifysas.premifyapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDropdownField(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(id = R.color.primary_color),
                unfocusedBorderColor = if (selectedOption.isNotBlank()) colorResource(id = R.color.primary_color) else colorResource(id = R.color.secondary_color),
                focusedLabelColor = colorResource(id = R.color.primary_color),
                unfocusedLabelColor = if (selectedOption.isNotBlank()) colorResource(id = R.color.primary_color) else colorResource(id = R.color.secondary_color),
                cursorColor = colorResource(id = R.color.primary_color),
                focusedTextColor = colorResource(id = R.color.primary_color),
                unfocusedTextColor = if (selectedOption.isNotBlank()) colorResource(id = R.color.primary_color) else colorResource(id = R.color.secondary_color)
            )
        )

        // Menú con fondo personalizado y borde
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(colorResource(id = R.color.back_color), shape = RoundedCornerShape(12.dp))
                .border(1.dp, colorResource(id = R.color.primary_color), RoundedCornerShape(12.dp))
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption, color = Color.Black) },
                    onClick = {
                        onOptionSelected(selectionOption)
                        expanded = false
                    }
                )
            }
        }
    }
}

