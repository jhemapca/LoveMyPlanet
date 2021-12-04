package com.example.lovemyplanet.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.lovemyplanet.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class PerfilAuspFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_perfil_ausp, container, false)
        val dbU = FirebaseAuth.getInstance()
        val db= FirebaseFirestore.getInstance()
        val etNombre:EditText=view.findViewById(R.id.etNombAuspProf)
        val etEncargado:EditText=view.findViewById(R.id.etEncargadoAuspProf)
        val etDir:EditText=view.findViewById(R.id.etDirAuspProf)
        val etPhone:EditText=view.findViewById(R.id.etTelefonoAuspProf)
        val etCorreo:EditText=view.findViewById(R.id.etCorreoAuspProf)
        val etPassword:EditText=view.findViewById(R.id.etContraAuspProf)
        val btnEditProf: FloatingActionButton =view.findViewById(R.id.buttonEditProfAusp)
        var editing:Boolean = false
        val user: FirebaseUser? = dbU.getCurrentUser()
        val idUser:String = user!!.uid
        db.collection("Auspiciadores").document(idUser).addSnapshotListener{snapshot, e ->
            if (e != null) {
                Log.w("FIREBASE WARNING", "Listen failed.", e)
                return@addSnapshotListener
            }
            if (snapshot != null && snapshot.exists()) {
                etNombre.setText(snapshot.data!!["nombre"].toString())
                etEncargado.setText(snapshot.data!!["encargado"].toString())
                etDir.setText(snapshot.data!!["direccion"].toString())
                etPhone.setText(snapshot.data!!["phone"].toString())
                etCorreo.setText(snapshot.data!!["correo"].toString())
                etPassword.setText(snapshot.data!!["contraseña"].toString())
            }else{
                Log.d("TAG", "Current data: null")
            }
        }
        btnEditProf.setOnClickListener {
            if(editing==false){
                etDir.isEnabled = true
                etDir.setBackgroundColor(Color.parseColor("#B2EEA2"))
                etEncargado.isEnabled=true
                etEncargado.setBackgroundColor(Color.parseColor("#B2EEA2"))
                etPhone.isEnabled=true
                etPhone.setBackgroundColor(Color.parseColor("#B2EEA2"))
                btnEditProf.setImageResource(android.R.drawable.ic_menu_save)
                editing=true
            }else{
                etDir.isEnabled = false
                etDir.setBackgroundColor(Color.parseColor("#ffffff"))
                etEncargado.isEnabled=false
                etEncargado.setBackgroundColor(Color.parseColor("#ffffff"))
                etPhone.isEnabled=false
                etPhone.setBackgroundColor(Color.parseColor("#ffffff"))
                btnEditProf.setImageResource(android.R.drawable.ic_menu_edit)
                editing=false

                var map:HashMap<String, String> = HashMap()
                map.put("encargado",etEncargado.text.toString())
                map.put("direccion",etDir.text.toString())
                map.put("phone",etPhone.text.toString())

                db.collection("Auspiciadores").document(idUser).update(map as Map<String, Any>)
            }
        }

        return view
    }

}