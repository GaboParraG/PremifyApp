package com.premifysas.premifyapp.ui.raffles

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.premifysas.premifyapp.R
import com.premifysas.premifyapp.model.Raffle
import com.premifysas.premifyapp.navigation.AppScreens
import com.premifysas.premifyapp.ui.theme.Poppins


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RafflesScreen(
    navController: NavController,
    viewModel: RafflesViewModel = hiltViewModel()
){

    val raffles by viewModel.raffles.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var raffleToDelete by remember { mutableStateOf<Raffle?>(null) }


    LaunchedEffect(Unit) {
        viewModel.loadRaffles()
    }

    Scaffold(

        topBar = {
            CenterAlignedTopAppBar(
                title = {  Icon(
                    painter = painterResource(id = R.drawable.vertical_azul),
                    contentDescription = null
                ) },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Menu"
                        )

                    }

                },
                actions = {
                    Icon(
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = "Menu"
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
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(AppScreens.NewRaffle.route)
                },
                containerColor = colorResource(id = R.color.primary_color),
                contentColor = colorResource(id = R.color.white),
                elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 12.dp)
            ) { Icon(imageVector = Icons.Filled.Add, contentDescription = "Add") } },
            modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .background(color = colorResource(id = R.color.purple_700))
    )
    { paddingValues ->


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Text(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .align(Alignment.CenterHorizontally),
                text = "Mis Rifas",
                style = TextStyle(
                    color = colorResource(R.color.primary_color),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = Poppins
                )
            )

            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(), // ocupa toda la pantalla
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = colorResource(id = R.color.primary_color),
                        strokeWidth = 4.dp
                    )
                }
            } else {

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)

                ) {
                    items(raffles) { raffle ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                            .clickable {
                            navController.currentBackStackEntry?.savedStateHandle?.set("raffle", raffle)
                            navController.navigate(AppScreens.RaffleDetail.route)
                        },
                            shape = RoundedCornerShape(10.dp),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Box(modifier = Modifier.fillMaxWidth()) {

                                IconButton(
                                    onClick = {
                                        raffleToDelete = raffle
                                        showDialog = true
                                    },
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .padding(8.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Eliminar",
                                        tint = colorResource(id = R.color.back_color)
                                    )
                                }

                                // 📄 Contenido del Raffle
                                Column(
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .align(Alignment.Center),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = raffle.name,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 20.sp
                                    )
                                    Text(text = "Premio: ${raffle.prize}")
                                    Text(text = "Costo: ${raffle.cost}")
                                    Text(text = "Método de pago: ${raffle.payment_method}")
                                    Text(text = "Fecha: ${raffle.date}")
                                    Text(text = "Estado: ${raffle.status}")
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showDialog && raffleToDelete != null) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("¿Eliminar Rifa?") },
            text = { Text("¿Estás seguro que deseas eliminar la rifa '${raffleToDelete?.name}'? Esta acción no se puede deshacer.") },
            confirmButton = {
                Button(onClick = {
                    viewModel.deleteRaffle(raffleToDelete!!.id)
                    showDialog = false
                }) {
                    Text("Aceptar")
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}
