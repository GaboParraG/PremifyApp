package com.premifysas.premifyapp.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.premifysas.premifyapp.login.ui.LoginScreen
import com.premifysas.premifyapp.splash.ui.SplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.premifysas.premifyapp.login.ui.LoginViewModel
import com.premifysas.premifyapp.newraffle.ui.NewRaffleScreen
import com.premifysas.premifyapp.ppal.ui.PpalScreen
import com.premifysas.premifyapp.raffles.RafflesScreen
import com.premifysas.premifyapp.signup.ui.SingUpScreen

@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun AppNavigation(){

    val navController = rememberNavController()
    
    NavHost(
        navController = navController,
        startDestination = AppScreens.SplasScreen.route){
        composable(route = AppScreens.SplasScreen.route){
            SplashScreen(navController)
        }
        composable(route = AppScreens.PpalScreen.route){
            PpalScreen(navController)
        }
        composable(route = AppScreens.Login.route){
            LoginScreen(
                navController,
                onSuccess = {
                    navController.navigate(AppScreens.PpalScreen.route) {
                        popUpTo(AppScreens.Login.route) { inclusive = true }
                    }
                }
            )
        }
        composable(route = AppScreens.SignUp.route) {
            SingUpScreen(
                navController,
                onRegistered = {
                    navController.navigate(AppScreens.Login.route) {
                        popUpTo(AppScreens.SignUp.route) { inclusive = true }
                    }
                }
                )

        }
        composable(route = AppScreens.NewRaffle.route) {
            NewRaffleScreen(navController)
        }
        composable(route = AppScreens.Raffles.route) {
            RafflesScreen(navController)
        }
        composable(route = AppScreens.Profile.route) {}

    }

}