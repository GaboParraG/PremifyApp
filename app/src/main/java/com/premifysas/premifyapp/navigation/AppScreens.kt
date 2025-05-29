package com.premifysas.premifyapp.navigation

sealed class AppScreens (val route: String) {

    object  SplasScreen: AppScreens("SplashScreen")
    object  PpalScreen: AppScreens("PpalScreen")
    object  Login: AppScreens("Login")
    object  SignUp: AppScreens("SignUp")

}