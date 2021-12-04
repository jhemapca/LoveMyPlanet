package com.example.lovemyplanet.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.lovemyplanet.CustomAdapter
import com.example.lovemyplanet.R
import com.example.lovemyplanet.models.ActividadesMiniModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class PerfilFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_perfil, container, false)

        val dbU = FirebaseAuth.getInstance()
        val db= FirebaseFirestore.getInstance()

        val etNombre:EditText=view.findViewById(R.id.etNombProfile)
        val etDireccion:EditText=view.findViewById(R.id.etDireccionProfile)
        val etBornDate:EditText=view.findViewById(R.id.etBornDateProfile)
        val etGenero:EditText=view.findViewById(R.id.etGeneroProfile)
        val correo:EditText=view.findViewById(R.id.etCorreoProfile)
        val contraseña:EditText=view.findViewById(R.id.etContraseñaProfile)
        val btnEditProf:FloatingActionButton=view.findViewById(R.id.buttonEditProf)
        var editing:Boolean = false
        val user: FirebaseUser? = dbU.getCurrentUser()
        val idUser:String = user!!.uid
        db.collection("Voluntarios").document(idUser).addSnapshotListener{snapshot, e ->
            if (e != null) {
                Log.w("FIREBASE WARNING", "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                etNombre.setText(snapshot.data!!["nombre"].toString())
                etBornDate.setText(snapshot.data!!["fechaNac"].toString())
                etDireccion.setText(snapshot.data!!["direccion"].toString())
                etGenero.setText(snapshot.data!!["sexo"].toString())
                correo.setText(snapshot.data!!["correo"].toString())
                contraseña.setText(snapshot.data!!["contraseña"].toString())
            } else {
                Log.d("TAG", "Current data: null")
            }
        }
        btnEditProf.setOnClickListener {
            if(editing==false){
                etDireccion.isEnabled = true
                etDireccion.setBackgroundColor(Color.parseColor("#B2EEA2"))
                etNombre.isEnabled = true
                etNombre.setBackgroundColor(Color.parseColor("#B2EEA2"))
                btnEditProf.setImageResource(android.R.drawable.ic_menu_save)
                editing=true
            }else{
                etDireccion.isEnabled = false
                etDireccion.setBackgroundColor(Color.parseColor("#ffffff"))
                etNombre.isEnabled = false
                etNombre.setBackgroundColor(Color.parseColor("#ffffff"))
                btnEditProf.setImageResource(android.R.drawable.ic_menu_edit)
                editing=false

                var map:HashMap<String, String> = HashMap()
                map.put("nombre",etNombre.text.toString())
                map.put("direccion",etDireccion.text.toString())

                db.collection("Voluntarios").document(idUser).update(map as Map<String, Any>)
            }
        }

        return view
    }

}