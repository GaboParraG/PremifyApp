package com.premifysas.premifyapp.splash.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.premifysas.premifyapp.R
import com.premifysas.premifyapp.navigation.AppScreens
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(navController: NavController){

    LaunchedEffect(key1 = true) {
        delay(5000)
        navController.popBackStack()
        navController.navigate(AppScreens.Login.route)
    }
        Splash()
}

@Composable
fun Splash(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.white)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {
        Image(painter = painterResource(id = R.drawable.vertical_azul), contentDescription ="logo" )
        Text(
            "Bienvenid@s",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
    }


}



@Preview(showBackground = true)
@Composable
fun SplashScreenPreview(){
    Splash()
}