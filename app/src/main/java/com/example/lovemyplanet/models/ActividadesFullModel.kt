package com.example.lovemyplanet.models

data class ActividadesFullModel(
    val tipoActividad:String="",
    val fecha:String="",
    val horaIni:String="",
    val horaFin:String="",
    val puntosPlanet:Int=0,
    val maxParticipantes: Int=0,
    val descripcion: String="",
    val actualParticipantes:Int=0,
    val Auspiciador: String="",
    val lugar:String=""
)
