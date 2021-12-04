package com.example.lovemyplanet

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lovemyplanet.models.ActividadesMiniModel

class CustomAdapter(private var lstActividades: ArrayList<ActividadesMiniModel>,
                    private val listener:OnItemClickListener
):
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView), View.OnClickListener{
        var itemImage:ImageView=itemView.findViewById(R.id.imgActividad)
        var itemTitle:TextView=itemView.findViewById(R.id.txtTitulo)
        var itemFecha:TextView=itemView.findViewById(R.id.txtFecha)
        var itemPuntos:TextView=itemView.findViewById(R.id.txtPuntos)
        var itemHoraIni:TextView=itemView.findViewById(R.id.txtHoraIni)
        var itemHoraFin:TextView=itemView.findViewById(R.id.txtHoraFin)
        var txtid:TextView=itemView.findViewById(R.id.txtID)
        init{
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position:Int = adapterPosition
            if(position!=RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
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
        holder.txtid.text=itemActividad.id
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

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

}