package com.example.proyecto2moviles.Data.model

data class User (
    val id: String = "",
    val email: String = "",
    val name: String = "",
    val password: String = "",
    val benchPress : Int = 0,
    val shoulderPress : Int = 0,
    val snatch : Int = 0,
    val clean : Int = 0,
    val deadLift : Int = 0
)

