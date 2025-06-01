package com.premifysas.premifyapp.raffles

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.premifysas.premifyapp.R
import com.premifysas.premifyapp.navigation.AppScreens
import com.premifysas.premifyapp.ui.theme.Poppins


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RafflesScreen(navController: NavController){

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
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(AppScreens.NewRaffle.route)
                },
                containerColor = colorResource(id = R.color.primary_color),
                contentColor = colorResource(id = R.color.white),
                elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 12.dp)
            ) { Icon(imageVector = Icons.Filled.Add, contentDescription = "Add") } },
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
            val (textTittle)  = createRefs()

            Text(
                modifier = Modifier
                    .padding(top = 120.dp)
                    .constrainAs(textTittle) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                text = "Mis Rifas",
                style = TextStyle.Default.copy(
                    colorResource(R.color.primary_color),
                    fontSize = (30.sp),
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = Poppins
                )
            )


        }

    }
}