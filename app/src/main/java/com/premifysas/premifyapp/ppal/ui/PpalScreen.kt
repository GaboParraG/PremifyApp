package com.premifysas.premifyapp.ppal.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.premifysas.premifyapp.R
import com.premifysas.premifyapp.navigation.AppScreens
import com.premifysas.premifyapp.ui.theme.Poppins


@Composable
fun PpalScreen(navController: NavController) {

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.imagen_fondo_full), // Reemplaza con tu imagen
            contentDescription = "Fondo", // Opcional, pero útil para accesibilidad
            modifier = Modifier.graphicsLayer(
                alpha = 0.3f // Ajusta este valor para controlar la transparencia
            ).fillMaxSize(),
            contentScale = ContentScale.Crop, // O ContentScale.Fit según tu necesidad

        )

        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .fillMaxWidth()
                .background(color = colorResource(id = R.color.back_color))
        ) {
            val (boxTittle, textOne, textTwo, textThree, textFour, boxButton) = createRefs()

            Box(
                Modifier
                    .constrainAs(boxTittle) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                    }) {
                Image(
                    painter = painterResource(id = R.drawable.vertical_azul),
                    contentDescription = "tittle",
                    Modifier.padding(40.dp).width(400.dp).height(200.dp)
                )
            }

            Text(
                modifier = Modifier
                    .padding(top = 140.dp)
                    .constrainAs(textOne) {
                        top.linkTo(boxTittle.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(textOne.top)
                    },
                text = "LA SUERTE",
                style = TextStyle.Default.copy(
                    colorResource(R.color.primary_color),
                    fontSize = 64.sp,
                    drawStyle = Stroke(
                        miter = 10f,
                        width = 5f,
                        join = StrokeJoin.Round
                    ),
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Normal,
                    fontFamily = Poppins
                )
            )

            Text(
                modifier = Modifier
                    .constrainAs(textTwo) {
                        top.linkTo(textOne.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                text = "ES DEL QUE SE LA",
                fontSize = (30.sp),
                fontWeight = FontWeight.SemiBold,
                fontFamily = Poppins
            )

            Text(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .constrainAs(textThree) {
                        top.linkTo(textTwo.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                text = "JUEGA",
                style = TextStyle.Default.copy(
                    colorResource(R.color.primary_color),
                    fontSize = (60.sp),
                    fontWeight = FontWeight.Bold,
                    fontFamily = Poppins
                )
            )

            Button(
                modifier = Modifier
                    .constrainAs(boxButton) {
                        top.linkTo(textThree.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    },
                onClick = {
                    navController.navigate(AppScreens.Login.route)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.primary_color),
                    contentColor = colorResource(id = R.color.white),
                    disabledContainerColor = colorResource(id = R.color.white),
                    disabledContentColor = colorResource(id = R.color.secondary_color)
                )
            ) {
                Text(
                    text = "Ingresar",
                    fontSize = (20.sp),
                    fontFamily = FontFamily.Serif
                )
            }
            Text(
                modifier = Modifier
                    .clickable {
                        navController.navigate(AppScreens.SignUp.route)

                    }
                    .padding(top = 20.dp)
                    .constrainAs(textFour) {
                        top.linkTo(boxButton.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                text = "No tienes cuenta?",
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
