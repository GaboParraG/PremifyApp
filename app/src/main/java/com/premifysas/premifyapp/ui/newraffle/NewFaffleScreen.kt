package com.premifysas.premifyapp.ui.newraffle

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.premifysas.premifyapp.model.Raffle

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NewRaffleScreen(
    navController: NavController,
    viewModel: NewRaffleViewModel = hiltViewModel()
) {
    var name by remember { mutableStateOf("") }
    var cost by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var image by remember { mutableStateOf("") }
    var design by remember { mutableStateOf("") }
    var paymentMethod by remember { mutableStateOf("") }
    var prize by remember { mutableStateOf("") }
    var typeNumber by remember { mutableStateOf("") }

    val isSaving by viewModel.isSaving.collectAsState()
    val saveSuccess by viewModel.saveSuccess.collectAsState()
    val formIsValid = name.isNotBlank() &&
            cost.isNotBlank() &&
            date.isNotBlank() &&
            paymentMethod.isNotBlank() &&
            prize.isNotBlank()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Crear Nueva Rifa", fontSize = 22.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        // TextFields centrados
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth(0.9f)
        )
        OutlinedTextField(
            value = cost,
            onValueChange = { input ->
                // Solo aceptar números
                if (input.all { it.isDigit() }) {
                    cost = input
                }
            },
            label = { Text("Costo") },
            modifier = Modifier.fillMaxWidth(0.9f),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        )
        OutlinedTextField(
            value = date,
            onValueChange = { date = it },
            label = { Text("Fecha") },
            modifier = Modifier.fillMaxWidth(0.9f)
        )
        OutlinedTextField(
            value = image,
            onValueChange = { image = it },
            label = { Text("Imagen (URL)") },
            modifier = Modifier.fillMaxWidth(0.9f)
        )
        OutlinedTextField(
            value = design,
            onValueChange = { design = it },
            label = { Text("Diseño") },
            modifier = Modifier.fillMaxWidth(0.9f)
        )
        OutlinedTextField(
            value = paymentMethod,
            onValueChange = { paymentMethod = it },
            label = { Text("Método de Pago") },
            modifier = Modifier.fillMaxWidth(0.9f)
        )
        OutlinedTextField(
            value = prize,
            onValueChange = { prize = it },
            label = { Text("Premio") },
            modifier = Modifier.fillMaxWidth(0.9f)
        )
        OutlinedTextField(
            value = typeNumber,
            onValueChange = { typeNumber = it },
            label = { Text("Tipo / Número") },
            modifier = Modifier.fillMaxWidth(0.9f)
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (isSaving) {
            CircularProgressIndicator()
        } else {
            Button(
                onClick = {
                    val raffle = Raffle(
                        name = name,
                        cost = cost.toIntOrNull() ?: 0,
                        date = date,
                        image = image,
                        design = design,
                        payment_method = paymentMethod,
                        prize = prize,
                        type_number = typeNumber
                    )
                    viewModel.createRaffle(raffle)
                },
                modifier = Modifier.fillMaxWidth(0.9f),
                enabled = formIsValid // 👈 Aquí se controla el estado habilitado
            ) {
                Text("Guardar Rifa")
            }
        }
    }

    // ✅ Mostrar popup si guardó correctamente
    if (saveSuccess) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = {},
            title = { Text("Éxito") },
            text = { Text("¡Rifa guardada con éxito!") },
            confirmButton = {
                Button(onClick = {
                    viewModel.resetState()
                    navController.popBackStack()
                }) {
                    Text("Aceptar")
                }
            }
        )
    }
}


