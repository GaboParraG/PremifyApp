package com.premifysas.premifyapp.ppal.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.premifysas.premifyapp.R

@Composable
fun PpalScreen() {

    ConstraintLayout(modifier = Modifier.fillMaxSize().fillMaxWidth().background(color = colorResource(id = R.color.secondary_color))) {
        val (boxTittle, boxLogo, boxText) = createRefs()

        Box(Modifier.padding(top = 30.dp).width(300.dp).height(130.dp).constrainAs(boxTittle){
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(parent.top)
        }) {
            Image(
                painter = painterResource(id = R.drawable.horizontal_blanco),
                contentDescription = "tittle",
            )
        }

        Box(Modifier.padding(top = 10.dp).width(150.dp).height(250.dp).constrainAs(boxLogo){
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(boxTittle.bottom)
        }) {
            Image(
                painter = painterResource(id = R.drawable.pollo),
                contentDescription = "tittle",
            )
        }
    }
}


@Preview
@Composable
fun PpalScreenPreview(){
    PpalScreen()
}