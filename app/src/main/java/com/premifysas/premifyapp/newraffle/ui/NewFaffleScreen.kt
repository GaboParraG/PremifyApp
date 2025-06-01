package com.premifysas.premifyapp.newraffle.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.premifysas.premifyapp.R
import com.premifysas.premifyapp.ui.theme.Poppins

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NewRaffleScreen(navController: NavController) {
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
            modifier = Modifier
                .fillMaxSize()
                .fillMaxWidth()
                .background(color = colorResource(id = R.color.purple_700))
        )
        {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .fillMaxWidth()
                    .background(color = colorResource(id = R.color.back_color))
            )
            {
                val (
                    textTittle,
                    editTextName,
                    editTextNumbers,
                    editTextDate,
                    editTextPrize,
                    editTextPay,
                    editTextCost,
                    editTextCurrency
                ) = createRefs()

                Text(
                    modifier = Modifier
                        .padding(top = 120.dp)
                        .constrainAs(textTittle) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                    text = "Nueva Rifa",
                    style = TextStyle.Default.copy(
                        colorResource(R.color.primary_color),
                        fontSize = (30.sp),
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = Poppins
                    )
                )

                    var textEmail: String by remember { mutableStateOf("") }

                    TextField(
                        textEmail,
                        modifier = Modifier
                            .padding(top = 80.dp)
                            .constrainAs(editTextName){
                                top.linkTo(textTittle.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                            .border(1.dp, color = colorResource(id = R.color.primary_color),shape = RoundedCornerShape(50.dp)),
                        colors = TextFieldDefaults.colors(
                            focusedTextColor =  colorResource(id = R.color.primary_color),
                            unfocusedTextColor = colorResource(id = R.color.purple_500),
                            focusedContainerColor = colorResource(id = R.color.white),
                            unfocusedContainerColor = colorResource(id = R.color.white),
                            disabledContainerColor = colorResource(id = R.color.white),
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(50.dp),
                        onValueChange = {textEmail=it},
                        label = { Text("E-mail")},
                        singleLine = true,
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                    )



            }

        }
    }
