package com.example.lovemyplanet

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lovemyplanet.models.ActividadesMiniModel

class CustomAdapter(private var lstActividades: List<ActividadesMiniModel>):RecyclerView.Adapter<CustomAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var itemImage:ImageView
        var itemTitle:TextView
        var itemFecha:TextView
        var itemPuntos:TextView
        var itemHoraIni:TextView
        var itemHoraFin:TextView
        init {
            itemImage=itemView.findViewById(R.id.imgActividad)
            itemTitle=itemView.findViewById(R.id.txtTitulo)
            itemFecha=itemView.findViewById(R.id.txtFecha)
            itemPuntos=itemView.findViewById(R.id.txtPuntos)
            itemHoraIni=itemView.findViewById(R.id.txtHoraIni)
            itemHoraFin=itemView.findViewById(R.id.txtHoraFin)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, i: Int) {
        val itemActividad = lstActividades[i]
        holder.itemTitle.text =itemActividad.tipoActividad
        holder.itemFecha.text = itemActividad.fecha
        holder.itemHoraIni.text=itemActividad.horaI
        holder.itemHoraFin.text = itemActividad.horaF
        holder.itemPuntos.text = itemActividad.puntosPlanet.toString()
        if(itemActividad.tipoActividad=="Limpieza de Playa") {
            holder.itemImage.setImageResource(R.drawable.playas)
        }else if(itemActividad.tipoActividad=="Limpieza de Parque"){
            holder.itemImage.setImageResource(R.drawable.parques)
        }else if(itemActividad.tipoActividad=="Limpieza de Autopista"){
            holder.itemImage.setImageResource(R.drawable.pistas)
        }else{
            holder.itemImage.setImageResource(R.drawable.mini_bg_login)
        }
    }

    override fun getItemCount(): Int {
        return lstActividades.size
    }
}