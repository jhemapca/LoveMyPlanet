package com.example.lovemyplanet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val db= FirebaseFirestore.getInstance()

        val idDoc=intent.getStringExtra("id")
        val etTitulo:TextView=findViewById(R.id.txtTipoActDetail)
        etTitulo.text= "Not found"


//        db.collection("actividades").document(idDoc!!).addSnapshotListener{ snapshot, e ->
//            if (e != null) {
//                Log.w("FIREBASE WARNING", "Listen failed.", e)
//                return@addSnapshotListener
//            }
//            if (snapshot != null && snapshot.exists()) {
//                etTitulo.text = snapshot.data!!["tipoActividad"].toString()
//            }else {
//                etTitulo.text= "Not found"
//            }
//        }
    }
}