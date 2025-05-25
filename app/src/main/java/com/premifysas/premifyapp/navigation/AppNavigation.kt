package com.premifysas.premifyapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHost
import androidx.navigation.compose.rememberNavController
import com.premifysas.premifyapp.login.ui.LoginScreen
import com.premifysas.premifyapp.splash.ui.SplashScreen

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.premifysas.premifyapp.ppal.ui.PpalScreen

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
            LoginScreen()
        }

    }

}