package com.example.lovemyplanet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lovemyplanet.models.TiendaModel

class TiendaAdapter(private var lstTienda:List<TiendaModel>): RecyclerView.Adapter<TiendaAdapter.ViewHolder>()  {
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)
    {
        val ivProducto: ImageView = itemView.findViewById(R.id.ivProducto)
        val tvNombre: TextView = itemView.findViewById(R.id.tvNombre)
        val tvPrecio: TextView = itemView.findViewById(R.id.tvPrecio)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val LayoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(LayoutInflater.inflate(R.layout.item_tienda,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemTienda = lstTienda[position]
        holder.tvNombre.text=itemTienda.nombreProducto
        holder.tvPrecio.text=itemTienda.puntosPlanet
        Glide.with(holder.itemView)
            .load(itemTienda.imageURL)
            .into(holder.ivProducto)

    }

    override fun getItemCount(): Int {
        return  lstTienda.size
    }
}