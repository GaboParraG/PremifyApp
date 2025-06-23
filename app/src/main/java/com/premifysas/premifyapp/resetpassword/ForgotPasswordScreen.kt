package com.premifysas.premifyapp.resetpassword

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.premifysas.premifyapp.R

@Composable
fun ResetPasswordScreen(
    navController: NavController,
    viewModel: ForgotPasswordViewModel = hiltViewModel()
) {
    var email by remember { mutableStateOf("") }
    val resultMessage by viewModel.result
    var showDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }

    val isValidEmail = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .background(color = colorResource(id = R.color.back_color))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                Modifier
                    .padding(40.dp)
                    .width(500.dp)
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.vertical_azul),
                    contentDescription = "tittle"
                )
            }

            Text("Recuperar contraseña", fontSize = 24.sp, fontWeight = FontWeight.Bold)

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo electrónico") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorResource(id = R.color.primary_color),
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = colorResource(id = R.color.primary_color),
                    unfocusedLabelColor = Color.Gray,
                    cursorColor = colorResource(id = R.color.primary_color),
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                )
            )

            Button(
                onClick = {
                    viewModel.sendPasswordResetEmail(email)
                    dialogMessage = "Verifica tu correo si está registrado."
                    showDialog = true
                },
                enabled = isValidEmail,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isValidEmail) colorResource(id = R.color.primary_color) else colorResource(id = R.color.back_color),
                    contentColor = colorResource(id = R.color.white)
                ),
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
            ) {
                Text("Enviar correo de recuperación")
            }
        }

        if (showDialog) {
            androidx.compose.material3.AlertDialog(
                onDismissRequest = { showDialog = false },
                confirmButton = {
                    Button(onClick = { showDialog = false }) {
                        Text("Aceptar")
                    }
                },
                title = { Text("Información") },
                text = {
                    Text(
                        text = resultMessage ?: dialogMessage,
                        color = if ((resultMessage ?: "").contains("Se envió", ignoreCase = true)) Color.Green else Color.Red
                    )
                }
            )
        }
    }
}











