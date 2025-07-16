package com.premifysas.premifyapp.customs

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.premifysas.premifyapp.R
import com.premifysas.premifyapp.navigation.AppScreens
import com.premifysas.premifyapp.ui.login.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MiTopAppBar(navController: NavController, shouldShowBackButton:Boolean = true) {


    val viewModel: LoginViewModel = hiltViewModel()
    var menuExpanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = colorResource(id = R.color.back_color).copy(alpha = 0.9f), // Translúcido
                shape = RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)
            )
            .border(
                width = 1.dp,
                color = colorResource(id = R.color.primary_color),
                shape = RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)
            )
    ) {
        CenterAlignedTopAppBar(
            title = {
                Icon(
                    painter = painterResource(id = R.drawable.vertical_azul),
                    contentDescription = null
                )
            },
            navigationIcon = {
                if (shouldShowBackButton) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Atrás"
                        )
                    }
                }
            },
            actions = {
                Box {

                    IconButton(onClick = { menuExpanded = true }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Menú"
                        )
                    }

                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false },
                                modifier = Modifier
                                .background(
                                color = colorResource(id = R.color.back_color), // Fondo del menú
                        shape = RoundedCornerShape(8.dp)
                    )
                        .border(
                            width = 1.dp,
                            color = colorResource(id = R.color.primary_color),
                            shape = RoundedCornerShape(8.dp)
                        )
                    ) {
                        DropdownMenuItem(
                            text = { Text("Mi Cuenta") },
                            onClick = {
                                menuExpanded = false
                                navController.navigate(AppScreens.Profile.route)
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Politica de Privacidad") },
                            onClick = {
                                menuExpanded = false
                                navController.navigate(AppScreens.Profile.route)
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Terminos y Condiciones") },
                            onClick = {
                                menuExpanded = false
                                navController.navigate(AppScreens.Profile.route)
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Configuración") },
                            onClick = {
                                menuExpanded = false
                                //navController.navigate(AppScreens.Config.route)
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Cerrar sesión") },
                            onClick = {
                                menuExpanded = false
                                viewModel.logout() // 👈 Cierra sesión
                                navController.navigate(AppScreens.Login.route) {
                                    popUpTo(0) { inclusive = true } // Limpia todo el backstack
                                }
                            }
                        )
                    }
                }
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Color.Transparent,
                titleContentColor = colorResource(id = R.color.white),
                navigationIconContentColor = colorResource(id = R.color.white),
                actionIconContentColor = colorResource(id = R.color.white)
            )
        )
    }
}

