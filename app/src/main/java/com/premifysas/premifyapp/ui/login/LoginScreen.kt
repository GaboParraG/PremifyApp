package com.premifysas.premifyapp.ui.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.premifysas.premifyapp.R
import com.premifysas.premifyapp.navigation.AppScreens
import com.premifysas.premifyapp.ui.theme.Poppins
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel(),
    onSuccess: () -> Unit
) {

    val loginErrorMessage by viewModel.loginErrorMessage.collectAsState()

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
                .size(width = 360.dp, height = 450.dp),
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
                val (textTittle, textUser, textPass, btnButton,textReb) = createRefs()

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


                val textEmail: String by viewModel.email.observeAsState(initial = "")
                val password: String by viewModel.password.observeAsState(initial = "")
                val loginEnable: Boolean by viewModel.loginEnable.observeAsState(initial = false)

                TextField(
                    textEmail,
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
                        focusedLabelColor = colorResource(id = R.color.primary_color),
                        unfocusedLabelColor =  colorResource(id = R.color.primary_color),
                        focusedContainerColor = colorResource(id = R.color.white),
                        unfocusedContainerColor = colorResource(id = R.color.white),
                        disabledContainerColor = colorResource(id = R.color.white),
                        unfocusedIndicatorColor = colorResource(id = R.color.primary_color),
                        focusedIndicatorColor = colorResource(id = R.color.primary_color)
                    ),
                    onValueChange = {viewModel.onLoginChanged(it,password)},
                    label = { Text("E-mail")},
                    singleLine = true,
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                )
                val passwordBtn: String by viewModel.password.observeAsState("")
                TextField(
                    value = passwordBtn,
                    modifier = Modifier
                        .padding(top = 40.dp)
                        .constrainAs(textPass){
                            top.linkTo(textUser.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                    colors = TextFieldDefaults.colors(
                        focusedTextColor =  colorResource(id = R.color.primary_color),
                        focusedLabelColor = colorResource(id = R.color.primary_color),
                        unfocusedLabelColor =  colorResource(id = R.color.primary_color),
                        unfocusedTextColor = colorResource(id = R.color.primary_color),
                        focusedContainerColor = colorResource(id = R.color.white),
                        unfocusedContainerColor = colorResource(id = R.color.white),
                        disabledContainerColor = colorResource(id = R.color.white),
                        unfocusedIndicatorColor = colorResource(id = R.color.primary_color),
                        focusedIndicatorColor = colorResource(id = R.color.primary_color)
                    ),
                    onValueChange = {viewModel.onLoginChanged(textEmail,it)},
                    label = { Text("Password")},
                    singleLine = true,
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = PasswordVisualTransformation(),
                )
                Button(modifier = Modifier
                    .padding(top = 60.dp)
                    .constrainAs(btnButton){
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(textPass.bottom)
                },
                    enabled = (loginEnable),
                    onClick = {
                        println("LOGIN BUTTON PRESSED")
                        viewModel.login(textEmail,passwordBtn)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.primary_color),
                        contentColor = colorResource(id = R.color.white),
                        disabledContainerColor = colorResource(id = R.color.back_color),
                        disabledContentColor = colorResource(id = R.color.white)
                    )
                ){
                    Text(
                        text = "Ingresar",
                        fontSize = (20.sp),
                        fontFamily = FontFamily.Serif
                    )
                }
                Text(
                    modifier = Modifier
                        .clickable {
                            navController.navigate(AppScreens.ForgotPassword.route)
                        }
                        .padding(top = 20.dp)
                        .constrainAs(textReb)
                        {
                            top.linkTo(btnButton.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                    text = "Olvido su Contraseña?",
                    style = TextStyle.Default.copy(
                        colorResource(R.color.primary_color),
                        fontSize = (20.sp),
                        fontWeight = FontWeight.Medium,
                        fontFamily = Poppins,
                        textDecoration = TextDecoration.Underline

                    )
                )

            }
        }
    }

    val loginState by viewModel.loginState.collectAsState()
    val isAdmin by viewModel.isAdmin.collectAsState()

    if (loginState == "success" && isAdmin == null) {
        viewModel.checkIfUserIsAdmin()
    }

    if (isAdmin != null) {
        onSuccess()
        viewModel.clearLoginState()
        if (isAdmin == true) {
            navController.navigate(AppScreens.Raffles.route)
        } else {
            navController.navigate(AppScreens.ActiveRaffles.route)
        }
    }

    val isLoading by viewModel.isLoading.observeAsState(false)
    if (isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(id = R.color.white).copy(alpha = 0.6f)), // semitransparente
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = colorResource(id = R.color.primary_color),
                strokeWidth = 4.dp
            )
        }
    }

    if (loginErrorMessage != null) {
        androidx.compose.material3.AlertDialog(
            onDismissRequest = { viewModel.clearLoginError() },
            title = { Text("Error de inicio de sesión") },
            text = { Text(loginErrorMessage ?: "") },
            confirmButton = {
                Button(onClick = { viewModel.clearLoginError() }) {
                    Text("Aceptar")
                }
            }
        )
    }
}


