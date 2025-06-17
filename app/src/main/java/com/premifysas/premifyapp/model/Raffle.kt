package com.premifysas.premifyapp.model

import java.io.Serializable

data class Raffle(
    var id: String = "",
    val name: String = "",
    val cost: Int =0,
    val date: String = "",
    val image: String = "",
    val design: String = "",
    val payment_method: String = "",
    val prize: String = "",
    val type_number: String = "",
    var status: Boolean = false
): Serializable
//ActiveRafflesScreen