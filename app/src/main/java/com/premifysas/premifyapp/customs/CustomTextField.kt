package com.premifysas.premifyapp.customs

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.premifysas.premifyapp.R

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    onClick: (() -> Unit)? = null
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .then(
                if (onClick != null) Modifier.clickable { onClick() }
                else Modifier
            )
            .border(
                width = 1.dp,
                color = colorResource(id = R.color.primary_color),
                shape = RoundedCornerShape(50.dp)
            ),
        label = { Text(label) },
        enabled = enabled,
        readOnly = readOnly,
        singleLine = true,
        maxLines = 1,
        shape = RoundedCornerShape(50.dp),
        colors = TextFieldDefaults.colors(
            focusedTextColor = colorResource(id = R.color.primary_color),
            unfocusedTextColor = colorResource(id = R.color.purple_500),
            focusedContainerColor = colorResource(id = R.color.white),
            unfocusedContainerColor = colorResource(id = R.color.white),
            disabledContainerColor = colorResource(id = R.color.white),
            disabledIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        ),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
    )
}