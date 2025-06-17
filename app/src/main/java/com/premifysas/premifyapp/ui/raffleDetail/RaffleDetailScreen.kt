package com.premifysas.premifyapp.ui.raffleDetail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.premifysas.premifyapp.R
import com.premifysas.premifyapp.model.Raffle
import com.premifysas.premifyapp.ui.theme.Poppins


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RaffleDetailScreen(navController: NavController) {

    val selectedNumbers = remember { mutableStateOf(mutableSetOf<Int>()) }
    val raffle = navController.previousBackStackEntry
        ?.savedStateHandle
        ?.get<Raffle>("raffle")

    if (raffle == null) {
        Text("No se pudo cargar la rifa.")
        return
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Icon(
                        painter = painterResource(id = R.drawable.vertical_azul),
                        contentDescription = null
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Menu"
                        )
                    }
                },
                actions = {
                    Icon(
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = "Opciones"
                    )
                },
                colors = TopAppBarColors(
                    containerColor = colorResource(id = R.color.primary_color),
                    scrolledContainerColor = colorResource(id = R.color.back_color),
                    navigationIconContentColor = colorResource(id = R.color.white),
                    titleContentColor = colorResource(id = R.color.white),
                    actionIconContentColor = colorResource(id = R.color.white)
                )
            )
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
                    onClick = { /* Acción de continuar */ },
                    modifier = Modifier.fillMaxWidth()
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
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = raffle.name, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    Text(text = "Premio: ${raffle.prize}", fontSize = 16.sp)
                    Text(text = "Costo: ${raffle.cost}", fontSize = 16.sp)
                    Text(text = "Fecha: ${raffle.date}", fontSize = 16.sp)
                    Text(text = "Método de pago: ${raffle.payment_method}", fontSize = 16.sp)
                }
            }
            // Card con números
            Card(
                modifier = Modifier
                    .fillMaxSize(),
                elevation = CardDefaults.cardElevation(4.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(5),
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize(),
                    content = {
                        items(100) { index ->
                            val number = index + 1
                            val isSelected = number in selectedNumbers.value

                            Card(
                                modifier = Modifier
                                    .padding(6.dp)
                                    .size(50.dp)
                                    .clickable {
                                        if (isSelected) {
                                            selectedNumbers.value.remove(number)
                                        } else {
                                            selectedNumbers.value.add(number)
                                        }
                                        // Actualiza el estado manualmente porque es un Set mutable
                                        selectedNumbers.value = selectedNumbers.value.toMutableSet()
                                    },
                                shape = CircleShape,
                                elevation = CardDefaults.cardElevation(2.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (isSelected)
                                        colorResource(id = R.color.primary_color)
                                    else
                                        colorResource(id = R.color.white)
                                )
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Text(
                                        text = number.toString(),
                                        color = if (isSelected)
                                            colorResource(id = R.color.white)
                                        else
                                            colorResource(id = R.color.black)
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