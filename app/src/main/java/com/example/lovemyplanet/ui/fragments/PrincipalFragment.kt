package com.example.lovemyplanet.ui.fragments


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lovemyplanet.CrearActividadActivity
import com.example.lovemyplanet.CustomAdapter
import com.example.lovemyplanet.MainMenu
import com.example.lovemyplanet.R
import com.example.lovemyplanet.models.ActividadesMiniModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore

class PrincipalFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_principal, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rvActividades)
        val fabCrearAct=view.findViewById<FloatingActionButton>(R.id.fabCrearNuevaActividad)
        val db = FirebaseFirestore.getInstance()
        val lstActividades: ArrayList<ActividadesMiniModel> = ArrayList()
        val dbU = FirebaseAuth.getInstance()
        val user: FirebaseUser? = dbU.getCurrentUser()
        val idUser:String = user!!.uid


        db.collection("actividades").addSnapshotListener{snapshot, e->
            if(e!=null)
            {
                Log.w("FirebaseWarning","ERROR",e)
                return@addSnapshotListener
            }
            for(dc in snapshot!!.documentChanges){
                when(dc.type) {
                    DocumentChange.Type.ADDED ->{
                        lstActividades.add(ActividadesMiniModel(
                            dc.document.data["tipoActividad"].toString(),
                            dc.document.data["fecha"].toString(),
                            dc.document.data["horaIni"].toString(),
                            dc.document.data["horaFin"].toString(),
                            dc.document.data["puntosPlanet"].toString().toInt()
                        ))
                        recyclerView.adapter=CustomAdapter(lstActividades)
                    }DocumentChange.Type.MODIFIED ->{
                        lstActividades.add(ActividadesMiniModel(
                            dc.document.data["tipoActividad"].toString(),
                            dc.document.data["fecha"].toString(),
                            dc.document.data["horaIni"].toString(),
                            dc.document.data["horaFin"].toString(),
                            dc.document.data["puntosPlanet"].toString().toInt()
                        ))
                        recyclerView.adapter=CustomAdapter(lstActividades)
                    }DocumentChange.Type.REMOVED ->{
                        Log.w("Firebase Warning","REMOVED")
                    }
                }
            }

            recyclerView.layoutManager = LinearLayoutManager(requireContext())

        }

        fabCrearAct.setOnClickListener {
            val intent = Intent(requireContext(), CrearActividadActivity::class.java)
            startActivity(intent)
        }
        db.collection("Voluntarios").document(idUser).addSnapshotListener{snapshot, e ->
            if (e != null) {
                Log.w("FIREBASE WARNING", "Listen failed.", e)
                return@addSnapshotListener
            }
            if (snapshot != null && snapshot.exists()) {
                fabCrearAct.visibility=View.INVISIBLE
            }

        }

        return view
    }
}