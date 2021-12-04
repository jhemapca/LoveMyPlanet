package com.example.lovemyplanet.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lovemyplanet.TiendaAdapter
import com.example.lovemyplanet.R
import com.example.lovemyplanet.models.TiendaModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore

class TiendaFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View=inflater.inflate(R.layout.fragment_tienda, container, false)
        // Inflate the layout for this fragment
        val db= FirebaseFirestore.getInstance()
        val lstTienda: ArrayList<TiendaModel> = ArrayList()
        val rvTienda: RecyclerView = view.findViewById(R.id.rvTienda)
        val btv: Button = view.findViewById(R.id.btnComprar)
        btv.setOnClickListener{
            Snackbar.make(btv,"Agregado al carrito",Snackbar.LENGTH_LONG)
                .setAction("Cancel"){}
                .show()
        }
        db.collection("productosTienda")
            .addSnapshotListener{snapshot,e->
                if(e!=null)
                {
                    Log.w("Firebase Warning","error",e)
                    return@addSnapshotListener
                }
                for(dc in snapshot!!.documentChanges)
                {
                    when(dc.type){
                        DocumentChange.Type.ADDED ->{

                            lstTienda.add(
                                TiendaModel(dc.document.data["nombreProducto"].toString(),
                                    dc.document.data["puntosPlanet"].toString(),
                                    dc.document.data["imageURL"].toString()))
                            rvTienda.adapter = TiendaAdapter(lstTienda)

                        }
                        DocumentChange.Type.MODIFIED->{
                            lstTienda.add(
                                TiendaModel(dc.document.data["nombreProducto"].toString(),
                                    dc.document.data["imageURL"].toString(),
                                    dc.document.data["puntosPlanet"].toString()))
                            rvTienda.adapter = TiendaAdapter(lstTienda)
                        }
                        DocumentChange.Type.REMOVED->{
                            Log.w("Firebase Warning","REMOVED")
                        }
                    }
                }
                rvTienda.layoutManager= LinearLayoutManager(requireContext())
            }
        return view
    }
}