package com.premifysas.premifyapp.ui.raffleDetail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.premifysas.premifyapp.R
import com.premifysas.premifyapp.customs.MiTopAppBar
import com.premifysas.premifyapp.model.Raffle
import com.premifysas.premifyapp.navigation.AppScreens
import com.premifysas.premifyapp.ui.theme.Poppins


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RaffleDetailScreen(navController: NavController) {

    val db = Firebase.firestore
    val lockedNumbers = remember { mutableStateOf(setOf<Int>()) }

    val selectedNumbers = remember { mutableStateOf(mutableSetOf<Int>()) }
    val isButtonEnabled by remember { derivedStateOf { selectedNumbers.value.isNotEmpty() } }

    val raffle = navController.previousBackStackEntry
        ?.savedStateHandle
        ?.get<Raffle>("raffle")

    if (raffle == null) {
        Text("No se pudo cargar la rifa.")
        return
    }
    val viewModel: RafflesDetailViewModel = hiltViewModel()
    val usedNumbers by viewModel.usedNumbers

    LaunchedEffect(raffle.name) {
        viewModel.loadUsedNumbers(raffle.name)
    }

    LaunchedEffect(Unit) {
        db.collection("selected_numbers").get().addOnSuccessListener { result ->
            val allLocked = result.flatMap { it.get("numbers") as? List<Long> ?: emptyList() }
                .map { it.toInt() }
                .toSet()
            lockedNumbers.value = allLocked
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
                    .navigationBarsPadding(), // 👈 evita que el botón quede oculto
                contentAlignment = Alignment.Center
            ){
                androidx.compose.material3.Button(
                    onClick = {
                        navController.currentBackStackEntry?.savedStateHandle?.set("selectedNumbers", selectedNumbers.value.toList())
                        navController.currentBackStackEntry?.savedStateHandle?.set("raffleName", raffle.name)
                        navController.navigate(AppScreens.ConfirmRaffle.route)
                    },
                    enabled = isButtonEnabled, // 🔑 Aquí lo controlas
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.primary_color),
                        contentColor = colorResource(id = R.color.white),
                        disabledContainerColor = colorResource(id = R.color.back_color),
                        disabledContentColor = colorResource(id = R.color.white)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(text = "Continuar")
                }
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {

            Text(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .align(Alignment.CenterHorizontally),
                text = "Detalle",
                style = TextStyle(
                    color = colorResource(R.color.primary_color),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = Poppins
                )
            )
            // Card de información
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                elevation = CardDefaults.cardElevation(6.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = colorResource(id = R.color.back_color) // Fondo personalizado
                ),
                border = BorderStroke(1.dp, colorResource(id = R.color.primary_color))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Column(
                        modifier = Modifier.weight(1f), // ocupa el máximo espacio posible
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(text = raffle.name, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                        Text(text = "Premio: ${raffle.prize}", fontSize = 16.sp)
                        Text(text = "Costo: ${raffle.cost}", fontSize = 16.sp)
                        Text(text = "Fecha: ${raffle.date}", fontSize = 16.sp)
                        Text(text = "Método de pago: ${raffle.payment_method}", fontSize = 16.sp)
                    }
                    Image(
                        painter = painterResource(id = R.drawable.logo_azul), // asegúrate de tener esta imagen en res/drawable
                        contentDescription = "Imagen de rifa",
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                    )
                }


            }
            // Card con números
            Card(
                modifier = Modifier
                    .fillMaxSize(),
                elevation = CardDefaults.cardElevation(4.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = colorResource(id = R.color.back_color) // Fondo personalizado
                ),
                border = BorderStroke(1.dp, colorResource(id = R.color.primary_color))
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(5),
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize(),
                    content = {
                        items(100) { index ->
                            val number = index + 1
//                            val isLocked = number in lockedNumbers.value
//                            val isSelected = number in selectedNumbers.value
//                            val isUsed = usedNumbers.contains(number)
//                            val isDisabled = isLocked || isUsed

                            val isUsed = usedNumbers.contains(number)
                            val isSelected = selectedNumbers.value.contains(number)

                            Card(
                                modifier = Modifier
                                    .padding(6.dp)
                                    .size(50.dp)
                                    .then(
                                        if (!isUsed)
                                            Modifier.clickable {
                                                val updated = selectedNumbers.value.toMutableSet()
                                                if (isSelected) updated.remove(number) else updated.add(number)
                                                selectedNumbers.value = updated
                                            }
                                        else Modifier
                                    ),
                                shape = CircleShape,
                                elevation = CardDefaults.cardElevation(2.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = when {
                                        isUsed -> Color.Gray
                                        isSelected -> colorResource(id = R.color.primary_color)
                                        else -> colorResource(id = R.color.white)
                                    }
                                )
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Text(
                                        text = number.toString(),
                                        color = when {
                                            isUsed -> Color.White
                                            isSelected -> colorResource(id = R.color.white)
                                            else -> colorResource(id = R.color.black)
                                        }
                                    )
                                }
                            }


                        }
                    }
                )
            }
        }
    }
}