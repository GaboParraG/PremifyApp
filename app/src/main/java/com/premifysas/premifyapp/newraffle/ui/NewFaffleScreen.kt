package com.premifysas.premifyapp.newraffle.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation.Companion.keyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.premifysas.premifyapp.customs.CustomTextField
import com.premifysas.premifyapp.ui.theme.Poppins

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NewRaffleScreen(navController: NavController) {
    Scaffold(
        Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .background(color = colorResource(id = R.color.purple_700)), {
            CenterAlignedTopAppBar(
                title = {
                    Icon(
                        painter = painterResource(id = R.drawable.vertical_azul),
                        contentDescription = null
                    )
                },
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
        }
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
                iconDate,
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

            var textName: String by remember { mutableStateOf("") }
            var textNumbers by remember { mutableStateOf("") }
            var isExpanded by remember { mutableStateOf(false) }
            val numbers = arrayOf("100","1000")
            val valors = arrayOf("Nequi","Daviplata","Efectivo")
            var textDate: String by remember { mutableStateOf("") }
            var dateIcon: String by remember { mutableStateOf("") }
            var textPrize: String by remember { mutableStateOf("") }
            var textPay: String by remember { mutableStateOf("") }
            var textCost: String by remember { mutableStateOf("") }
            var textCurrency: String by remember { mutableStateOf("") }
            var isToggled by rememberSaveable { mutableStateOf(false) }
            val state = rememberDatePickerState()

            CustomTextField(
                value = textName,
                onValueChange = { textName = it },
                label = "Nombre Rifa",
                modifier = Modifier
                    .padding(top = 80.dp)
                    .constrainAs(editTextName) {
                        top.linkTo(textTittle.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            CustomTextField(
                value = textNumbers,
                onValueChange = { textNumbers = it },
                label = "Cuántos números desea?",
                enabled = false,
                readOnly = true,
                modifier = Modifier
                    .padding(top = 20.dp)
                    .constrainAs(editTextNumbers) {
                        top.linkTo(editTextName.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                onClick = { isExpanded = true }
            )

            CustomTextField(
                value = textPrize,
                onValueChange = { textPrize = it },
                label = "Premio",
                modifier = Modifier
                    .padding(top = 20.dp)
                    .constrainAs(editTextPrize) {
                        top.linkTo(editTextDate.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            CustomTextField(
                value = textDate,
                onValueChange = { textDate = it },
                label = "Fecha Sorteo DD/MM/AAAA",
                keyboardType = KeyboardType.Number,
                modifier = Modifier
                    .padding(top = 20.dp)
                    .constrainAs(editTextDate) {
                        top.linkTo(editTextNumbers.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )
            CustomTextField(
                value = textPay,
                onValueChange = { textPay = it },
                label = "Pago",
                modifier = Modifier
                    .padding(top = 20.dp)
                    .constrainAs(editTextPay) {
                        top.linkTo(editTextPrize.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)

                    },
                onClick = { isExpanded = true }

            )

            IconButton(
                modifier = Modifier
                    .constrainAs(iconDate) {
                        top.linkTo(editTextDate.top)
                        bottom.linkTo(editTextDate.bottom)
                        end.linkTo(editTextDate.end)
                    }.padding(top = 20.dp),
                onClick = {
                    isToggled = !isToggled

                }
            ) {
                Icon(
                    imageVector = if (isToggled) Icons.Default.DateRange else Icons.Default.DateRange,
                    contentDescription = if (isToggled) "Selected icon button" else "Unselected icon button."
                )
            }

            if (isToggled) {
                DatePickerDialog(
                    onDismissRequest = { isToggled = false },
                    confirmButton = {
                        Button(onClick = { isToggled = false }) {
                            Text("Confirm")
                        }
                    }
                ) {
                    DatePicker(state = state)
                }

            }
            val date = state.selectedDateMillis?.let { java.util.Date(it) }
            textDate = date?.let { java.text.SimpleDateFormat("dd/MM/yyyy").format(it) }.toString()


            DropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false }) {
                numbers.forEach { number ->
                    DropdownMenuItem(text = { Text(text = number.toString()) }, onClick = {
                        textNumbers = number.toString()
                        isExpanded = false
                    })
                }
       }

            DropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false }) {
                valors.forEach { valor ->
                    DropdownMenuItem(text = { Text(text = valor.toString()) }, onClick = {
                        textCurrency = valor.toString()
                        isExpanded = false
                    })
                }
            }


            }

        }
}

