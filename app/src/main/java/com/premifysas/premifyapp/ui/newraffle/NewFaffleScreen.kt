package com.premifysas.premifyapp.ui.newraffle

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.premifysas.premifyapp.R
import com.premifysas.premifyapp.customs.CustomDropdownField
import com.premifysas.premifyapp.customs.CustomOutlinedTextField
import com.premifysas.premifyapp.customs.DateInputField
import com.premifysas.premifyapp.customs.MiTopAppBar
import com.premifysas.premifyapp.model.Raffle
import com.premifysas.premifyapp.ui.theme.Poppins
import java.io.ByteArrayOutputStream
import com.google.firebase.storage.ktx.storage
import com.google.firebase.ktx.Firebase

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.res.painterResource

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
    var prizeList by remember { mutableStateOf(listOf("")) }
    var typeNumber by remember { mutableStateOf("") }



    val paymentOptions = listOf("Nequi", "Bancolombia", "Efectivo", "Daviplata")

    val isSaving by viewModel.isSaving.collectAsState()
    val saveSuccess by viewModel.saveSuccess.collectAsState()
    val formIsValid = name.isNotBlank() &&
            cost.isNotBlank() &&
            date.isNotBlank() &&
            paymentMethod.isNotBlank() &&
            prizeList.isNotEmpty()

    val context = LocalContext.current
    val imageUri = remember { mutableStateOf<Uri?>(null) }


    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            uploadImageToFirebase(
                uri = it,
                raffleName = name,
                onSuccess = { downloadUrl ->
                    imageUri.value = uri
                    image = downloadUrl
                },
                onFailure = { e ->
                    // Manejo de error si deseas
                }
            )
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            val uri = bitmapToUri(context, bitmap)
            uploadImageToFirebase(
                uri = uri,
                raffleName = name,
                onSuccess = { downloadUrl ->
                    imageUri.value = uri
                    image = downloadUrl
                },
                onFailure = { e ->
                    // Manejo de error si deseas
                }
            )
        }
    }

    Scaffold(
        topBar = {
            MiTopAppBar(navController)
        },
        bottomBar = {
            if (isSaving) {
                CircularProgressIndicator()
            } else {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .navigationBarsPadding(), // 👈 evita que el botón quede oculto
                    contentAlignment = Alignment.Center
                ) {
                    androidx.compose.material3.Button(
                        onClick = {
                            val raffle = Raffle(
                                name = name,
                                cost = cost.toIntOrNull() ?: 0,
                                date = date,
                                image = image,
                                design = design,
                                payment_method = paymentMethod,
                                prize = prizeList,
                                type_number = typeNumber
                            )
                            viewModel.createRaffle(raffle)
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        enabled = formIsValid,// 👈 Aquí se controla el estado habilitado
                        colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.primary_color),
                        contentColor = colorResource(id = R.color.white),
                        disabledContainerColor = colorResource(id = R.color.back_color),
                        disabledContentColor = colorResource(id = R.color.white)
                    ),
                    shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Crear Rifa")
                    }

                }
            }
        }
    ) { paddingValues ->

        val scrollState = rememberScrollState()

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // usa el padding que entrega Scaffold
                .padding(horizontal = 16.dp), // padding lateral personalizado
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(
                    text = "Crear Nueva Rifa",
                    style = TextStyle(
                        color = colorResource(R.color.primary_color),
                        fontSize = 30.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = Poppins
                    ),
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

            item {
                CustomOutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = "Nombre",
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                CustomOutlinedTextField(
                    value = cost,
                    onValueChange = { input -> if (input.all { it.isDigit() }) cost = input },
                    label = "Costo",
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    )
                )
            }

            item {
                DateInputField(
                    date = date,
                    onDateSelected = { date = it }
                )
            }

            item {
                Column {
                    Text("Imagen", fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { galleryLauncher.launch("image/*") }) {
                            Icon(
                                imageVector = Icons.Default.Home,
                                contentDescription = "Galería"
                            )
                        }
                        IconButton(onClick = { cameraLauncher.launch() }) {
                            Icon(
                                imageVector = Icons.Default.Face,
                                contentDescription = "Cámara"
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    imageUri.value?.let { uri ->
                        androidx.compose.foundation.Image(
                            painter = rememberAsyncImagePainter(uri),
                            contentDescription = "Imagen seleccionada",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                        )
                    }
                }
            }

            item {
                CustomOutlinedTextField(
                    value = design,
                    onValueChange = { design = it },
                    label = "Diseño",
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                CustomDropdownField(
                    label = "Método de Pago",
                    options = paymentOptions,
                    selectedOption = paymentMethod,
                    onOptionSelected = { paymentMethod = it },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 4.dp)
                ) {
                    Text(
                        text = "Premios",
                        style = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 18.sp),
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = { prizeList = prizeList + "" }) {
                        Icon(Icons.Default.Add, contentDescription = "Agregar premio", tint = colorResource(id = R.color.primary_color))
                    }
                }
            }

            itemsIndexed(prizeList) { index, prize ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    CustomOutlinedTextField(
                        value = prize,
                        onValueChange = { newValue ->
                            prizeList = prizeList.toMutableList().also { it[index] = newValue }
                        },
                        label = "Premio ${index + 1}",
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    if (prizeList.size > 1) {
                        IconButton(
                            onClick = {
                                prizeList = prizeList.toMutableList().also { it.removeAt(index) }
                            }
                        ) {
                            Icon(Icons.Default.Delete, contentDescription = "Eliminar premio", tint = Color.Red)
                        }
                    }
                }
            }

            item {
                CustomOutlinedTextField(
                    value = typeNumber,
                    onValueChange = { typeNumber = it },
                    label = "Tipo / Número",
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(100.dp)) // para que no quede muy pegado al botón
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
}

fun bitmapToUri(context: Context, bitmap: Bitmap): Uri {
    val filename = "temp_image_${System.currentTimeMillis()}.jpg"
    val stream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
    val path = MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, filename, null)
    return Uri.parse(path)
}

fun uploadImageToFirebase(
    uri: Uri,
    raffleName: String,
    onSuccess: (String) -> Unit,
    onFailure: (Exception) -> Unit
) {
    val cleanName = raffleName.trim().replace("\\s+".toRegex(), "_")
    val storageRef = Firebase.storage.reference
    val imageRef = storageRef.child("raffle_images/${cleanName}.jpg")

    imageRef.putFile(uri)
        .addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                onSuccess(downloadUri.toString())
            }
        }
        .addOnFailureListener { exception ->
            onFailure(exception)
        }
}


