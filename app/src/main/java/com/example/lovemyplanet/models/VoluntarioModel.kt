package com.example.lovemyplanet.models

data class VoluntarioModel(
    val nombre:String="",
    val apellidos:String="",
    val direccion:String="",
    val fechaNac:String="",
    val sexo:String="",
    val correo:String="",
    val contraseña:String="",
    val puntosPlanet:Int=0,
    val rol:Boolean = false
)
