package com.premifysas.premifyapp.ui.confirmraffle

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.premifysas.premifyapp.R
import com.premifysas.premifyapp.customs.MiTopAppBar
import com.premifysas.premifyapp.ui.theme.Poppins

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmRaffleScreen(
    navController: NavController
) {
    val selectedNumbers = navController.previousBackStackEntry
        ?.savedStateHandle
        ?.get<List<Int>>("selectedNumbers") ?: emptyList()

    val raffleName = navController.previousBackStackEntry
        ?.savedStateHandle
        ?.get<String>("raffleName") ?: ""

    val viewModel: ConfirmRaffleViewModel = hiltViewModel()
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var showImage by remember { mutableStateOf(false) }

    val isSaving by viewModel.isSaving.collectAsState()
    val saveResult by viewModel.saveResult.collectAsState()

    var showSuccessDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }

    val sendEmailEvent by viewModel.sendEmailEvent
    val context = LocalContext.current

    val errorMessage by viewModel.errorMessage

    val imageLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
        imageUri = it
        showImage = it != null
    }

    val isFormValid = selectedNumbers.isNotEmpty()
            && name.isNotBlank()
            && phone.isNotBlank()
            && address.isNotBlank()
            && imageUri != null

    LaunchedEffect(sendEmailEvent) {
        if (sendEmailEvent) {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "message/rfc822"
                putExtra(Intent.EXTRA_EMAIL, arrayOf("gabrieldavidparrag@gmail.com"))
                putExtra(Intent.EXTRA_SUBJECT, "Nueva confirmación – $raffleName")
                putExtra(
                    Intent.EXTRA_TEXT,
                    """
                Información del participante:

                Nombre: $name
                Teléfono: $phone
                Dirección: $address
                Rifa: $raffleName
                Números: ${selectedNumbers.joinToString(", ")}

                ${imageUri?.let { "Imagen adjunta: incluida." } ?: ""}
                """.trimIndent()
                )
                imageUri?.let {
                    putExtra(Intent.EXTRA_STREAM, it)
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                }
            }

            try {
                context.startActivity(Intent.createChooser(intent, "Enviar correo con..."))
            } catch (e: Exception) {
                e.printStackTrace()
            }

            viewModel.resetEmailEvent()
        }
    }

    LaunchedEffect(saveResult) {
        when (saveResult) {
            true -> showSuccessDialog = true
            false -> showErrorDialog = true
            else -> {}
        }
    }

    Scaffold(
        topBar = {
            MiTopAppBar(navController)
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .navigationBarsPadding(),
                contentAlignment = Alignment.Center
            ) {
                if (isSaving) {
                    CircularProgressIndicator()
                } else {
                    Button(
                        onClick = {
                            viewModel.saveData(
                                selectedNumbers = selectedNumbers,
                                name = name,
                                phone = phone,
                                address = address,
                                imageUri = imageUri,
                                raffleName = raffleName
                            )
                        },
                        enabled = isFormValid,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.primary_color),
                            contentColor = colorResource(id = R.color.white),
                            disabledContainerColor = colorResource(id = R.color.back_color),
                            disabledContentColor = colorResource(id = R.color.white)
                        )
                    ) {
                        Text(text = "Confirmar")
                    }
                }
            }
        }
    ) { paddingValues ->

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Text(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .align(Alignment.CenterHorizontally),
                text = "Confirmacion de sorteo",
                style = TextStyle(
                    color = colorResource(R.color.primary_color),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = Poppins,
                    textAlign = TextAlign.Center
                )
            )
            Text(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .align(Alignment.CenterHorizontally),
                text = "Numeros Escogidos",
                style = TextStyle(
                    color = colorResource(R.color.secondary_color),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = Poppins,
                    textAlign = TextAlign.Center
                )
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                LazyRow(
                    modifier = Modifier.wrapContentWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(selectedNumbers) { number ->
                        Card(
                            shape = CircleShape,
                            colors = CardDefaults.cardColors(containerColor = (colorResource(id = R.color.primary_color))),
                            modifier = Modifier.size(48.dp),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Text(
                                    text = number.toString(),
                                    color = Color.White,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = {
                        if (it.matches(Regex("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]*$"))) {
                            name = it
                        }
                    },
                    label = { Text("Nombre") },
                    singleLine = true,
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

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = phone,
                    onValueChange = {
                        if (it.length <= 10 && it.all { char -> char.isDigit() }) {
                            phone = it
                        }
                    },
                    label = { Text("Teléfono") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), // Teclado numérico
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
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text("Dirección") },
                    singleLine = false,
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colorResource(id = R.color.primary_color),    // Borde activo
                        unfocusedBorderColor = Color.Gray,                                 // Borde inactivo
                        focusedLabelColor = colorResource(id = R.color.primary_color),     // Label activo
                        unfocusedLabelColor = Color.Gray,                                  // Label inactivo
                        cursorColor = colorResource(id = R.color.primary_color),           // Color del cursor
                        focusedTextColor = Color.Black,                                    // Texto activo
                        unfocusedTextColor = Color.Black                                   // Texto inactivo
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))

                imageUri?.let { uri ->
                    Box(
                        modifier = Modifier
                            .size(200.dp) // 🔺 Aumenta el tamaño de la miniatura
                            .padding(8.dp)
                            .align(Alignment.CenterHorizontally) // Opcional: centrado horizontal
                    ) {
                        AsyncImage(
                            model = uri,
                            contentDescription = "Imagen seleccionada",
                            modifier = Modifier
                                .matchParentSize()
                                .clip(RoundedCornerShape(16.dp)) // Bordes más redondeados
                                .border(2.dp, colorResource(id = R.color.primary_color), RoundedCornerShape(16.dp)),
                            contentScale = ContentScale.Crop
                        )

                        IconButton(
                            onClick = { imageUri = null }, // Eliminar imagen
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .size(28.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = "Eliminar imagen",
                                tint = Color.Red
                            )
                        }
                    }
                }
                }
            Button(
                onClick = { imageLauncher.launch("image/*") },
                enabled = imageUri == null,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (imageUri == null) colorResource(id = R.color.primary_color) else colorResource(id = R.color.back_color),
                    contentColor = Color.White, // Color del texto
                    disabledContainerColor = colorResource(id = R.color.back_color),
                    disabledContentColor = colorResource(id = R.color.white)
                )
            ) {
                Text("Seleccionar imagen")
            }
            }
        }

        if (showSuccessDialog) {
            AlertDialog(
                onDismissRequest = { showSuccessDialog = false },
                confirmButton = {
                    Button(onClick = {
                        showSuccessDialog = false
                        navController.popBackStack()
                    }) {
                        Text("Aceptar")
                    }
                },
                title = { Text("Éxito") },
                text = { Text("La información fue guardada correctamente.") }
            )
        }

        if (showErrorDialog) {
            AlertDialog(
                onDismissRequest = { showErrorDialog = false },
                confirmButton = {
                    Button(onClick = { showErrorDialog = false }) {
                        Text("Aceptar")
                    }
                },
                title = { Text("Error") },
                text = { Text("Ocurrió un error al guardar la información.") }
            )
        }


    if (errorMessage != null) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = {
                viewModel.clearErrorMessage()
            },
            confirmButton = {
                Button(onClick = {
                    viewModel.clearErrorMessage()
                }) {
                    Text("Aceptar")
                }
            },
            title = { Text("Error") },
            text = { Text(errorMessage ?: "Ocurrió un error inesperado.") }
        )
    }
}