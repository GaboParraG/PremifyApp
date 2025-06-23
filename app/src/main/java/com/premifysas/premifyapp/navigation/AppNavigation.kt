package com.premifysas.premifyapp.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.premifysas.premifyapp.ui.login.LoginScreen
import com.premifysas.premifyapp.ui.splash.SplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.premifysas.premifyapp.resetpassword.ResetPasswordScreen
import com.premifysas.premifyapp.ui.confirmraffle.ConfirmRaffleScreen
import com.premifysas.premifyapp.ui.newraffle.NewRaffleScreen
import com.premifysas.premifyapp.ui.ppal.PpalScreen
import com.premifysas.premifyapp.ui.raffles.RafflesScreen
import com.premifysas.premifyapp.ui.singup.SingUpScreen
import com.premifysas.premifyapp.ui.raffleDetail.RaffleDetailScreen
import com.premifysas.premifyapp.ui.raffles.ActiveRafflesScreen

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
        composable(route = AppScreens.RaffleDetail.route) {
            RaffleDetailScreen(navController)
        }
        composable(route = AppScreens.Raffles.route) {
            RafflesScreen(navController)
        }
        composable(route = AppScreens.ActiveRaffles.route) {
            ActiveRafflesScreen(navController)
        }
        composable(route = AppScreens.ConfirmRaffle.route) {
            ConfirmRaffleScreen(navController)
        }
        composable(route = AppScreens.Profile.route) {}

        composable(AppScreens.ForgotPassword.route) {
            ResetPasswordScreen(navController)
        }

    }

}