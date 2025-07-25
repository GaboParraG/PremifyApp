package com.premifysas.premifyapp.ui.singup

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.premifysas.premifyapp.R
import com.premifysas.premifyapp.ui.theme.Poppins

@Composable
fun SingUpScreen(
    navController: NavController,
    viewModel: SignUpViewModel = hiltViewModel(),
    onRegistered: () -> Unit
    ){

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }

    val state by viewModel.registerState.collectAsState()

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .background(color = colorResource(id = R.color.back_color))
    )
    {
        val (boxTittle, boxButton) = createRefs()
        Box(

            Modifier
            .constrainAs(boxTittle) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(parent.top)
            }
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

        Card(
            modifier = Modifier
                .padding(bottom = 90.dp)
                .constrainAs(boxButton){
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                }
                .size(width = 360.dp, height = 480.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            ),
            shape = RoundedCornerShape(10),
            border = BorderStroke(1.dp, color = colorResource(id = R.color.primary_color)),
            elevation = CardDefaults.cardElevation(disabledElevation = 20.dp)
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                .background(color = colorResource(id = R.color.white)))
            {
                val (textTittle, textUser, textPass,textConfirmPass, btnButton,textReb) = createRefs()

                Text(
                    modifier = Modifier
                        .padding(top = 40.dp)
                        .constrainAs(textTittle) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                    text = "Bienvenido",
                    style = TextStyle.Default.copy(
                        colorResource(R.color.primary_color),
                        fontSize = (30.sp),
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = Poppins
                    )
                )


                TextField(
                    email,
                    modifier = Modifier
                        .padding(top = 40.dp)
                        .constrainAs(textUser){
                            top.linkTo(textTittle.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                    colors = TextFieldDefaults.colors(
                        focusedTextColor =  colorResource(id = R.color.primary_color),
                        unfocusedTextColor = colorResource(id = R.color.primary_color),
                        focusedContainerColor = colorResource(id = R.color.white),
                        unfocusedContainerColor = colorResource(id = R.color.white),
                        disabledContainerColor = colorResource(id = R.color.white),
                        unfocusedIndicatorColor = colorResource(id = R.color.primary_color),
                        focusedIndicatorColor = colorResource(id = R.color.primary_color)
                    ),
                    onValueChange = {email=it},
                    label = { Text("E-mail")},
                    singleLine = true,
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )

                TextField(
                    password,
                    modifier = Modifier
                        .padding(top = 40.dp)
                        .constrainAs(textPass){
                            top.linkTo(textUser.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                    colors = TextFieldDefaults.colors(
                        focusedTextColor =  colorResource(id = R.color.primary_color),
                        unfocusedTextColor = colorResource(id = R.color.primary_color),
                        focusedContainerColor = colorResource(id = R.color.white),
                        unfocusedContainerColor = colorResource(id = R.color.white),
                        disabledContainerColor = colorResource(id = R.color.white),
                        unfocusedIndicatorColor = colorResource(id = R.color.primary_color),
                        focusedIndicatorColor = colorResource(id = R.color.primary_color)
                    ),
                    onValueChange = {password=it},
                    label = { Text("Password")},
                    singleLine = true,
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )


                TextField(
                    name,
                    modifier = Modifier
                        .padding(top = 40.dp)
                        .constrainAs(textConfirmPass){
                            top.linkTo(textPass.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                    colors = TextFieldDefaults.colors(
                        focusedTextColor =  colorResource(id = R.color.primary_color),
                        unfocusedTextColor = colorResource(id = R.color.primary_color),
                        focusedContainerColor = colorResource(id = R.color.white),
                        unfocusedContainerColor = colorResource(id = R.color.white),
                        disabledContainerColor = colorResource(id = R.color.white),
                        unfocusedIndicatorColor = colorResource(id = R.color.primary_color),
                        focusedIndicatorColor = colorResource(id = R.color.primary_color)
                    ),
                    onValueChange = {name=it},
                    label = { Text("Nombre completo")},
                    singleLine = true,
                    maxLines = 1
                )
                Button(modifier = Modifier
                    .padding(top = 60.dp)
                    .constrainAs(btnButton){
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(textConfirmPass.bottom)
                },
                    onClick = {
                        viewModel.register(email, password, name)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.primary_color),
                        contentColor = colorResource(id = R.color.white),
                        disabledContainerColor = colorResource(id = R.color.white),
                        disabledContentColor = colorResource(id = R.color.secondary_color)
                    )
                ){
                    Text(
                        text = "Registrar",
                        fontSize = (20.sp),
                        fontFamily = FontFamily.Serif
                    )
                }

            }
        }
    }

    if (state == "registered") {
        onRegistered()
    } else if (state == "error") {
        Text("Hubo un error al registrar")
    }
}
